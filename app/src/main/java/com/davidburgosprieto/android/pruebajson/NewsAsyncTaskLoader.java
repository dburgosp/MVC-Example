package com.davidburgosprieto.android.pruebajson;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.os.OperationCanceledException;
import android.util.Log;

import java.util.ArrayList;

public class NewsAsyncTaskLoader extends AsyncTaskLoader<ArrayList<News>> {
    private final String TAG = NewsAsyncTaskLoader.class.getSimpleName();

    // Member variables.
    private ArrayList<News> news;

    /**
     * Constructor for this class.
     *
     * @param context is the context of the activity.
     * @param news    is the list of movies.
     */
    public NewsAsyncTaskLoader(Context context, ArrayList<News> news) {
        super(context);
        this.news = news;
    }

    /**
     * Subclasses must implement this to take care of loading their data,
     * as per {@link #startLoading()}. This is not called by clients directly,
     * but as a result of a call to {@link #startLoading()}.
     */
    @Override
    protected void onStartLoading() {
        String methodTag = TAG + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
        if (news != null) {
            Log.i(methodTag, "Reload existing results.");
            deliverResult(news);
        } else {
            Log.i(methodTag, "Load new results.");
            forceLoad();
        }
    }

    /**
     * Called on a worker thread to perform the actual load and to return the result of the load
     * operation.
     *
     * @return The result of the load operation.
     * @throws OperationCanceledException if the load is canceled during execution.
     * @see #isLoadInBackgroundCanceled
     * @see #cancelLoadInBackground
     * @see #onCanceled
     */
    @Override
    public ArrayList<News> loadInBackground() {
        return NewsJSONUtils.getNews();
    }

    /**
     * Sends the result of the load to the registered listener. Should only be called by subclasses.
     * Must be called from the process's main thread.
     *
     * @param news the result of the load
     */
    @Override
    public void deliverResult(ArrayList<News> news) {
        String methodTag = TAG + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
        if (news == null || news.size() == 0)
            Log.i(methodTag, "No results to deliver.");
        else
            Log.i(methodTag, "Results fetched and delivered.");
        this.news = news;
        super.deliverResult(this.news);
    }
}
