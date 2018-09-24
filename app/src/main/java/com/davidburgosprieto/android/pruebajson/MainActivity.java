package com.davidburgosprieto.android.pruebajson;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    private final String TAG = MainActivity.class.getSimpleName();
    public static final int LOADER_ID = 0;
    private ArrayList<News> news;
    private Context context;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayout firstNewsLayout;
    private TextView loadingTextView;
    private NewsListAdapter newsListAdapter;
    private Loader<ArrayList<News>> loader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        // Initialise layout elements.
        progressBar = (ProgressBar) findViewById(R.id.news_loading_progress_bar);
        firstNewsLayout = (LinearLayout) findViewById(R.id.first_news_layout);
        recyclerView = (RecyclerView) findViewById(R.id.news_recycler_view);
        loadingTextView = (TextView) findViewById(R.id.news_loading_text_view);
        firstNewsLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        loadingTextView.setVisibility(View.GONE);

        // Set the LayoutManager for the RecyclerView.
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Set the onClickMoviesListener for click events in the adapters.
        NewsListAdapter.OnItemClickListener newsClickListener =
                new NewsListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(News item, View clickedView) {
                        Toast.makeText(context, "News clicked", Toast.LENGTH_SHORT).show();
                    }
                };

        // Set the Adapter for the RecyclerView.
        newsListAdapter = new NewsListAdapter(new ArrayList<News>(), newsClickListener);
        recyclerView.setAdapter(newsListAdapter);

        // Create an AsyncTaskLoader for retrieving the list of news.
        if (loader == null) {
            // If this is the first time, init loader.
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        } else {
            // If it is not the first time, restart loader.
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int i, @Nullable Bundle bundle) {
        if (NetworkUtils.isConnected(context)) {
            // Loading...
            progressBar.setVisibility(View.VISIBLE);
            loadingTextView.setText(R.string.loading_data);
            loadingTextView.setVisibility(View.VISIBLE);

            // There is an available connection. Fetch news from server.
            loader = new NewsAsyncTaskLoader(context, news);
            return loader;
        } else {
            // There is no connection. Show error message.
            loadingTextView.setText(R.string.no_connection);
            loadingTextView.setVisibility(View.VISIBLE);
            return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> data) {
        String methodTag = TAG + "." + Thread.currentThread().getStackTrace()[2].getMethodName();

        // Hide progress bar.
        progressBar.setVisibility(View.GONE);

        // Check if there is an available connection.
        if (NetworkUtils.isConnected(context)) {
            // Hide loading TextView.
            loadingTextView.setVisibility(View.GONE);

            // If there is a valid result, display it on its corresponding layouts.
            news = data;
            if (news != null && news.size() > 0) {
                Log.i(methodTag, "Search results for news not null.");

                // Order results by date.
                Collections.sort(news, new Comparator<News>() {
                    @Override
                    public int compare(News news2, News news1) {
                        return news1.getDate().compareTo(news2.getDate());
                    }
                });

                // Display newer news result.
                setFirstNews(news.get(0));

                // Display the other news.
                ArrayList<News> otherNews = news;
                otherNews.remove(0);
                newsListAdapter.updateNewsArrayList(otherNews);
                recyclerView.setVisibility(View.VISIBLE);

            } else {
                // No data has been fetched from server.
                loadingTextView.setText(R.string.no_data);
            }
        } else {
            // There is no connection. Show error message.
            loadingTextView.setText(R.string.no_connection);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
    }

    /**
     * Helper method for displaying newer news.
     *
     * @param newerNews is the {@link News} element to be displayed.
     */
    private void setFirstNews(News newerNews) {
        firstNewsLayout.setVisibility(View.VISIBLE);

        // News ticker and header.
        TextView tickerAndHeaderTextView = (TextView) findViewById(R.id.first_news_ticker_and_header);
        String htmlText = "";
        String newsTicker = newerNews.getNewsTicker();
        String header = newerNews.getHeader();
        if (!newsTicker.equals("")) {
            htmlText = "<strong>" + newsTicker + "</strong>";
            if (!header.equals(""))
                htmlText = htmlText + " · " + header;
        } else if (!header.equals(""))
            htmlText = header;
        if (!htmlText.equals(""))
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                tickerAndHeaderTextView.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
            } else {
                tickerAndHeaderTextView.setText(Html.fromHtml(htmlText));
            }
        else
            tickerAndHeaderTextView.setVisibility(View.GONE);

        // News title.
        TextView titleTextView = (TextView) findViewById(R.id.first_news_title);
        String title = newerNews.getTitle();
        if (!title.equals(""))
            titleTextView.setText(title);
        else
            titleTextView.setVisibility(View.GONE);


        // News date.
        TextView dateTextView = (TextView) findViewById(R.id.first_news_date);
        View lineView = findViewById(R.id.first_news_line);
        String date = DateTimeUtils.getStringFromDate(newerNews.getDate(),
                DateTimeUtils.DATE_FORMAT_FULL);
        if (!title.equals(""))
            dateTextView.setText(date);
        else {
            // No date. Hide dateTextView and lineView.
            dateTextView.setVisibility(View.GONE);
            lineView.setVisibility(View.GONE);
        }

        // News image.
        ImageView imageView = (ImageView) findViewById(R.id.first_news_image);
        String image = newerNews.getImage();
        if (!image.equals("")) {
            // Calculate image size for scaleType="centerCrop", depending on display size and 
            // original image width and height.
            final DisplayUtils displayUtils = new DisplayUtils(context);
            int widthPixels = displayUtils.getFullDisplayBackdropWidthPixels();
            int heightPixels = (widthPixels *
                    newerNews.getImageHeight() / newerNews.getImageWidth());

            // Display image using Picasso. If there is any error, show default image
            // R.drawable.no_backdrop.
            Picasso.with(context)
                    .load(image)
                    .resize(widthPixels, heightPixels)
                    .centerCrop()
                    .error(R.drawable.no_backdrop)
                    .into(imageView);
        } else
            imageView.setVisibility(View.GONE);
    }
}
