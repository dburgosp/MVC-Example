package com.davidburgosprieto.android.pruebajson.model;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.davidburgosprieto.android.pruebajson.common.BaseObservableViewMvc;
import com.davidburgosprieto.android.pruebajson.common.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FetchNewsListUseCase
        extends BaseObservableViewMvc<FetchNewsListUseCase.Listener>
        implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    public interface Listener {
        void onNewsListFetched(ArrayList<News> news);

        void onNoNewsListFetched();

        void onNoConnection();
    }

    public static final int LOADER_ID = 0;
    private Loader<ArrayList<News>> mLoader = null;
    private final Context mContext;
    private final Activity mActivity;
    private ArrayList<News> mNews;

    /**
     * Constructor for this class.
     *
     * @param activity is the calling activity.
     */
    public FetchNewsListUseCase(Activity activity) {
        mActivity = activity;
        mContext = activity.getBaseContext();
    }

    /**
     * Public method for starting the fetching process.
     */
    public void fetchNewsListAndNotify() {
        if (mLoader == null) {
            // If this is the first time, init loader.
            mActivity.getLoaderManager().initLoader(LOADER_ID, null, this);
        } else {
            // If it is not the first time, restart loader.
            mActivity.getLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        if (NetworkUtils.isConnected(mContext)) {
            // There is an available connection. Fetch news from server.
            return new NewsAsyncTaskLoader(mContext, mNews);
        } else {
            // There is no connection.
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        // Notify all the listeners for the result of the fetching operation.
        for (Listener listener : getListeners()) {
            // Check if there is an available connection.
            if (NetworkUtils.isConnected(mContext)) {
                // There is a valid result.
                mNews = data;
                if (mNews != null && mNews.size() > 0) {
                    // Order results by date.
                    Collections.sort(mNews, new Comparator<News>() {
                        @Override
                        public int compare(News news2, News news1) {
                            return news1.getDate().compareTo(news2.getDate());
                        }
                    });

                    // Return news list.
                    listener.onNewsListFetched(mNews);
                } else {
                    // No data has been fetched from server.
                    listener.onNoNewsListFetched();
                }
            } else {
                // There is no connection.
                listener.onNoConnection();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {

    }
}
