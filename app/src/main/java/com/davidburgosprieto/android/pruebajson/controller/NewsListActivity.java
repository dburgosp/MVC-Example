package com.davidburgosprieto.android.pruebajson.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.davidburgosprieto.android.pruebajson.model.News;
import com.davidburgosprieto.android.pruebajson.model.NewsAsyncTaskLoader;
import com.davidburgosprieto.android.pruebajson.view.NewsListViewMvc;
import com.davidburgosprieto.android.pruebajson.view.NewsListViewMvcImpl;
import com.davidburgosprieto.android.pruebajson.R;
import com.davidburgosprieto.android.pruebajson.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Controller class. It contains application layer logic and no UI layer logic. NewsListActivity
 * implements NewsListViewMvcImpl.Listener for receiving user interaction events managed by the
 * View class NewsListViewMvcImpl.
 */
public class NewsListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<News>>, NewsListViewMvc.Listener {

    private final String TAG = NewsListActivity.class.getSimpleName();
    public static final int LOADER_ID = 0;
    private ArrayList<News> news;
    private Loader<ArrayList<News>> loader = null;
    private NewsListViewMvc mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Migrate UI logic into the view and register for receiving UI events from the View class
        // NewsListViewMvcImpl.
        mViewMvc = new NewsListViewMvcImpl(LayoutInflater.from(this), null);
        mViewMvc.registerListener(this);
        setContentView(mViewMvc.getRootView());

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
        if (NetworkUtils.isConnected(this)) {
            // Loading...
            mViewMvc.setProgress(true);
            mViewMvc.setInfoMessage(R.string.loading_data);

            // There is an available connection. Fetch news from server.
            loader = new NewsAsyncTaskLoader(this, news);
            return loader;
        } else {
            // There is no connection. Show error message.
            mViewMvc.setInfoMessage(R.string.no_connection);
            return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> data) {
        String methodTag = TAG + "." + Thread.currentThread().getStackTrace()[2].getMethodName();

        // Hide progress bar.
        mViewMvc.setProgress(false);

        // Check if there is an available connection.
        if (NetworkUtils.isConnected(this)) {
            // Hide loading TextView.
            mViewMvc.setInfoMessage(0);

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
                mViewMvc.bindNewerNews(news);

                // Display the other news.
                mViewMvc.bindOtherNews(news);

                // TODO: show news elements playing some cool animation.
            } else {
                // No data has been fetched from server.
                mViewMvc.setInfoMessage(R.string.no_data);
            }
        } else {
            // There is no connection. Show error message.
            mViewMvc.setInfoMessage(R.string.no_connection);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
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
}