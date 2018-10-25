package com.davidburgosprieto.android.pruebajson.view.implementations;

import android.support.annotation.Nullable;
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
import com.davidburgosprieto.android.pruebajson.common.BaseObservableViewMvc;
import com.davidburgosprieto.android.pruebajson.common.utils.DateTimeUtils;
import com.davidburgosprieto.android.pruebajson.common.utils.NewsViewUtils;
import com.davidburgosprieto.android.pruebajson.controller.NewsListAdapter;
import com.davidburgosprieto.android.pruebajson.model.News;
import com.davidburgosprieto.android.pruebajson.view.dependencyinjection.ViewMvcFactory;
import com.davidburgosprieto.android.pruebajson.view.interfaces.NewsListItemViewMvc;
import com.davidburgosprieto.android.pruebajson.view.interfaces.NewsListViewMvc;

import java.util.ArrayList;

/**
 * View class for representing a list of news. It contains UI Layer logic and sends notifications
 * about user interaction events through the "Decoupling Layer" Listener interface (in fact this
 * interface really belongs to the UI Layer) to any Controller classes that need this information
 * (at present, {@link com.davidburgosprieto.android.pruebajson.controller.NewsListActivity}).
 */
public class NewsListViewMvcImpl extends BaseObservableViewMvc<NewsListViewMvc.Listener>
        implements NewsListItemViewMvc.Listener, NewsListViewMvc {

    private final ProgressBar mProgressBar;
    private final RecyclerView mRecyclerView;
    private final RelativeLayout mFirstNewsLayout;
    private final TextView mLoadingTextView, mHeaderTextView, mTitleTextView, mDateTextView;
    private final ImageView mImageView;
    private final NewsListAdapter mNewsListAdapter;

    /* ************ */
    /* CONSTRUCTORS */
    /* ************ */

    /**
     * Constructor for this class.
     *
     * @param inflater       is the LayoutInflater from the calling activity for instantiating
     *                       layout XML files into their corresponding View objects.
     * @param parent         is the root View of the generated hierarchy.
     * @param viewMvcFactory is the ViewMvcFactory for dependency injection, used for instantiating
     *                       other functional classes.
     */
    public NewsListViewMvcImpl(LayoutInflater inflater, @Nullable ViewGroup parent,
                               ViewMvcFactory viewMvcFactory) {
        // Use the setRootView method from the BaseViewMvc abstract class.
        setRootView(inflater.inflate(R.layout.activity_news_list, parent, false));

        // Get layout elements.
        mProgressBar = findViewById(R.id.news_loading_progress_bar);
        mFirstNewsLayout = findViewById(R.id.first_news_layout);
        mRecyclerView = findViewById(R.id.news_recycler_view);
        mLoadingTextView = findViewById(R.id.news_loading_text_view);
        mHeaderTextView = findViewById(R.id.first_news_ticker_and_header);
        mTitleTextView = findViewById(R.id.first_news_title);
        mDateTextView = findViewById(R.id.first_news_date);
        mImageView = findViewById(R.id.first_news_image);

        // Hide elements by default.
        mFirstNewsLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mLoadingTextView.setVisibility(View.GONE);

        // Create Adapter and init RecyclerView.
        mNewsListAdapter = new NewsListAdapter(new ArrayList<News>(), this, viewMvcFactory);
        initRecyclerView();
    }

    /* *************** */
    /* PRIVATE METHODS */
    /* *************** */

    /**
     * Private helper method for setting LayoutManager and Adapter for the RecyclerView.
     */
    private void initRecyclerView() {
        // Set the LayoutManager for the RecyclerView.
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        // Set the Adapter for the RecyclerView. The listener is this class, which implements
        // NewsListAdapter.OnItemClickListener, so clicks on adapter elements will be resolved in
        // the overridden onItemClick method.
        mRecyclerView.setAdapter(mNewsListAdapter);
    }

    /* ************** */
    /* PUBLIC METHODS */
    /* ************** */

    /**
     * Public method to be used from MVC controllers that instantiate objects of
     * {@link NewsListViewMvcImpl} class for showing or hiding the ProgressBar.
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
     * Public method to be used from MVC controllers that instantiate objects of
     * {@link NewsListViewMvcImpl} class for displaying an info message.
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
     * Public interface method that will be implemented in the Listeners that have been previously
     * subscribed through the getListeners() method from the BaseObservableViewMvc abstract class.
     *
     * @param item        is the News object with the clickedView info.
     * @param clickedView is the View that have received the click event.
     */
    @Override
    public void onItemClick(News item, View clickedView) {
        for (Listener listener : getListeners()) {
            listener.onItemClick(item, clickedView);
        }
    }

    /**
     * Public method to be used from MVC controllers that instantiate objects of
     * {@link NewsListViewMvcImpl} class for displaying the newer news in a separate layout.
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
     * Public method to be used from MVC controllers that instantiate objects of
     * {@link NewsListViewMvcImpl} class for displaying all news but the first one in the
     * RecyclerView.
     *
     * @param news is the array of {@link News}.
     */
    @Override
    public void bindOtherNews(ArrayList<News> news) {
        // Build the news array and load it into the adapter.
        ArrayList<News> otherNews = new ArrayList<>(news);
        otherNews.remove(0);
        mNewsListAdapter.updateNewsArrayList(otherNews);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}