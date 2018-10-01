package com.davidburgosprieto.android.pruebajson.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.davidburgosprieto.android.pruebajson.R;
import com.davidburgosprieto.android.pruebajson.controller.NewsListAdapter;
import com.davidburgosprieto.android.pruebajson.model.News;
import com.davidburgosprieto.android.pruebajson.utils.DateTimeUtils;
import com.davidburgosprieto.android.pruebajson.model.NewsViewUtils;

import java.util.ArrayList;

/**
 * View class for representing a list of news. It contains UI Layer logic and sends notifications
 * about user interaction events through the "Decoupling Layer" Listener interface (in fact this
 * interface really belongs to the UI Layer) to any Controller classes that need this information
 * (at present, {@link com.davidburgosprieto.android.pruebajson.controller.NewsListActivity}).
 */
public class NewsListViewMvcImpl implements NewsListItemViewMvc.Listener, NewsListViewMvc {

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private RelativeLayout mFirstNewsLayout;
    private TextView mLoadingTextView, mHeaderTextView, mTitleTextView, mDateTextView;
    private ImageView mImageView;
    private NewsListAdapter mNewsListAdapter;

    private final View mRootView;
    private final ArrayList<Listener> mListeners = new ArrayList<>();

    /**
     * Constructor for this class.
     *
     * @param inflater is the LayoutInflater from the calling activity for instantiating layout XML
     *                 files into their corresponding View objects.
     * @param parent   is the root View of the generated hierarchy.
     */
    public NewsListViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        mRootView = inflater.inflate(R.layout.activity_news_list, parent, false);

        // Initialise layout elements.
        mProgressBar = findViewById(R.id.news_loading_progress_bar);
        mFirstNewsLayout = findViewById(R.id.first_news_layout);
        mRecyclerView = findViewById(R.id.news_recycler_view);
        mLoadingTextView = findViewById(R.id.news_loading_text_view);
        mHeaderTextView = findViewById(R.id.first_news_ticker_and_header);
        mTitleTextView = findViewById(R.id.first_news_title);
        mDateTextView = findViewById(R.id.first_news_date);
        mImageView = findViewById(R.id.first_news_image);

        mFirstNewsLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mLoadingTextView.setVisibility(View.GONE);

        // Set the LayoutManager for the RecyclerView.
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        // Set the Adapter for the RecyclerView. The listener is this class, which implements
        // NewsListAdapter.OnItemClickListener, so clicks on adapter elements will be resolved in
        // the overridden onItemClick method.
        mNewsListAdapter = new NewsListAdapter(new ArrayList<News>(), this);
        mRecyclerView.setAdapter(mNewsListAdapter);
    }

    /**
     * Public helper method for showing or hiding the ProgressBar.
     *
     * @param onOff if true shows the ProgressBar, otherwise hides it.
     */
    @Override
    public void setProgress(boolean onOff) {
        if (onOff)
            mProgressBar.setVisibility(View.VISIBLE);
        else
            mProgressBar.setVisibility(View.GONE);
    }

    /**
     * Public helper method for displaying an info message.
     *
     * @param resourceId is the resource identifier for the text to be displayed. If the id equals 0
     *                   the corresponding TextView will be hidden.
     */
    @Override
    public void setInfoMessage(int resourceId) {
        if (resourceId > 0) {
            mLoadingTextView.setText(resourceId);
            mLoadingTextView.setVisibility(View.VISIBLE);
        } else
            mLoadingTextView.setVisibility(View.GONE);
    }

    /**
     * Public method for registering to onItemClick events.
     *
     * @param listener is the Listener that will be added to the list of Listeners.
     */
    @Override
    public void registerListener(Listener listener) {
        mListeners.add(listener);
    }

    /**
     * Public method for unregistering from receiving onItemClick events.
     *
     * @param listener is the Listener that will be removed from the list of Listeners.
     */
    @Override
    public void unregisterListener(Listener listener) {
        mListeners.remove(listener);
    }

    /**
     * Public interface method that will be implemented in the Listeners that have been previously
     * subscribed through {@link #registerListener(Listener)} method.
     *
     * @param item        is the News object with the clickedView info.
     * @param clickedView is the View that have received the click event.
     */
    @Override
    public void onItemClick(News item, View clickedView) {
        for (Listener listener : mListeners) {
            listener.onItemClick(item, clickedView);
        }
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    /**
     * Display the newer news in a separate layout.
     *
     * @param news is the array of news.
     */
    @Override
    public void bindNewerNews(ArrayList<News> news) {
        // Newer news come in the first element of the news array.
        final News currentNews = news.get(0);

        // News image.
        NewsViewUtils.setNewsImage(getContext(), mImageView, currentNews.getImage(),
                currentNews.getImageHeight(), currentNews.getImageWidth(), true);

        // News ticker and header.
        NewsViewUtils.setNewsTickerAndHeader(mHeaderTextView, currentNews.getNewsTicker(),
                currentNews.getHeader());

        // News title.
        NewsViewUtils.setText(mTitleTextView, currentNews.getTitle());

        // News date.
        NewsViewUtils.setText(mDateTextView, DateTimeUtils.getStringFromDate(currentNews.getDate(),
                DateTimeUtils.DATE_FORMAT_FULL));

        // Show layout.
        mFirstNewsLayout.setVisibility(View.VISIBLE);

        // Define behaviour when the newer news element is clicked.
        mFirstNewsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the interface onItemClick action, which be implemented later in the
                // listener class.
                onItemClick(currentNews, mFirstNewsLayout);
            }
        });
    }

    /**
     * Display all news but the first one in the RecyclerView.
     *
     * @param news is the array of news.
     */
    @Override
    public void bindOtherNews(ArrayList<News> news) {
        // Build the news array and load it into the adapter.
        ArrayList<News> otherNews = new ArrayList<>(news);
        otherNews.remove(0);
        mNewsListAdapter.updateNewsArrayList(otherNews);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Private custom getContext() method.
     *
     * @return the context of this activity.
     */
    private Context getContext() {
        return getRootView().getContext();
    }

    /**
     * Private custom findViewById method. Finds a view that was identified by the id attribute from
     * the XML that was processed in the constructor of this class.
     *
     * @param id is the resource identifier.
     * @return the view if found or null otherwise.
     */
    @SuppressWarnings("unchecked")
    private <T extends View> T findViewById(int id) {
        return (T) getRootView().findViewById(id);
    }
}