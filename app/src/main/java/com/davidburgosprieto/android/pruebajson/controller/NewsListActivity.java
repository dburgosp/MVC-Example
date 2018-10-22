package com.davidburgosprieto.android.pruebajson.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.davidburgosprieto.android.pruebajson.model.FetchNewsListUseCase;
import com.davidburgosprieto.android.pruebajson.model.News;
import com.davidburgosprieto.android.pruebajson.view.interfaces.NewsListViewMvc;
import com.davidburgosprieto.android.pruebajson.view.implementations.NewsListViewMvcImpl;
import com.davidburgosprieto.android.pruebajson.R;

import java.util.ArrayList;

/**
 * Controller class. It contains application layer logic and no UI layer logic. NewsListActivity
 * implements NewsListViewMvcImpl.Listener for receiving user interaction events managed by the
 * View class NewsListViewMvcImpl.
 */
public class NewsListActivity
        extends BaseActivity
        implements NewsListViewMvc.Listener, FetchNewsListUseCase.Listener {

    private NewsListViewMvc mViewMvc;
    private FetchNewsListUseCase mFetchNewsListUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Migrate UI logic into the view and register for receiving UI events from the View class
        // NewsListViewMvcImpl. Here we use dependency injection to achieve this, so we don't depend
        // on a specific implementation of NewsListViewMvc.
        mViewMvc = getCompositionRoot().getViewMvcFactory().getNewsListViewMvc(null);

        // Get the NewsAsyncTaskViewUseCase object for fetching the news list from the server.
        mFetchNewsListUseCase = getCompositionRoot().getNewsAsyncTaskViewUseCase();

        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Register for UI changes and show progress indicator.
        mViewMvc.registerListener(this);
        mViewMvc.setProgress(true);

        // Fetch the news and register for changes on this process.
        mFetchNewsListUseCase.registerListener(this);
        mFetchNewsListUseCase.fetchNewsListAndNotify();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Unregister all listeners for avoiding memory leaks.
        mViewMvc.unregisterListener(this);
        mFetchNewsListUseCase.unregisterListener(this);
    }

    /**
     * Implement interface {@link NewsListViewMvcImpl.Listener#onItemClick(News, View)} method here,
     * for resolving clicks on the most recent news item from this activity.
     */
    @Override
    public void onItemClick(News item, View clickedView) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", item.getUrl());
        startActivity(intent);
    }

    /**
     * Implement interface {@link FetchNewsListUseCase.Listener#onNewsListFetched(ArrayList)} method
     * here, for displaying the news list.
     *
     * @param news is the list of news received from the listener.
     */
    @Override
    public void onNewsListFetched(ArrayList<News> news) {
        // Hide progress indicator.
        mViewMvc.setProgress(false);

        // Display newer news result.
        mViewMvc.bindNewerNews(news);

        // Display the other news.
        mViewMvc.bindOtherNews(news);
    }

    /**
     * Implement interface {@link FetchNewsListUseCase.Listener#onNoNewsListFetched()} method here,
     * for displaying the corresponding message.
     */
    @Override
    public void onNoNewsListFetched() {
        // Hide progress indicator and show message.
        mViewMvc.setProgress(false);
        mViewMvc.setInfoMessage(R.string.no_data);
    }

    /**
     * Implement interface {@link FetchNewsListUseCase.Listener#onNoConnection()} method here, for
     * displaying the corresponding message.
     */
    @Override
    public void onNoConnection() {
        // Hide progress indicator and show message.
        mViewMvc.setProgress(false);
        mViewMvc.setInfoMessage(R.string.no_connection);
    }
}