package com.davidburgosprieto.android.pruebajson.controller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidburgosprieto.android.pruebajson.model.News;
import com.davidburgosprieto.android.pruebajson.view.NewsListItemViewMvc;
import com.davidburgosprieto.android.pruebajson.view.NewsListItemViewMvcImpl;

import java.util.ArrayList;

public class NewsListAdapter
        extends RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>
        implements NewsListItemViewMvc.Listener {
    private static final String TAG = NewsListAdapter.class.getSimpleName();
    private final NewsListItemViewMvc.Listener mListener;
    private ArrayList<News> mNewsArrayList;

    /**
     * Constructor for this class.
     *
     * @param newsArrayList is the list of news that will be represented into the adapter.
     * @param listener      is the listener for receiving the clicks.
     */
    public NewsListAdapter(ArrayList<News> newsArrayList, NewsListItemViewMvc.Listener listener) {
        mNewsArrayList = newsArrayList;
        mListener = listener;
    }

    /**
     * Public helper method to clear the current news arrayList.
     */
    void clearNewsArrayList() {
        mNewsArrayList.clear();
        notifyDataSetChanged();
    }

    /**
     * Setter method for updating the list of news in the adapter.
     *
     * @param newsArrayList is the new list of news.
     */
    public void updateNewsArrayList(ArrayList<News> newsArrayList) {
        mNewsArrayList.addAll(newsArrayList);
        notifyDataSetChanged();
    }

    /**
     * Called when RecyclerView needs a new {@link NewsListViewHolder} of the given type to represent
     * an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(NewsListViewHolder, int)
     */
    @Override
    public NewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Migrate UI logic into the view and register for receiving UI events from the View class
        // NewsListItemViewMvcImpl.
        NewsListItemViewMvc viewMvc = new NewsListItemViewMvcImpl(
                LayoutInflater.from(parent.getContext()), parent);
        viewMvc.registerListener(this);
        return new NewsListViewHolder(viewMvc);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the
     *                   item at the given position in the data set.
     * @param position   The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(NewsListViewHolder viewHolder, int position) {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log.i(TAG + "." + methodName, "Displaying data at position " + position);
        if (!mNewsArrayList.isEmpty()) {
            // Update NewsListViewHolder with the news at current position in the adapter.
            News currentNews = mNewsArrayList.get(position);
            viewHolder.mViewMvc.bindNews(currentNews);
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int itemCount = mNewsArrayList.size();
        Log.i(TAG + "." + methodName, "Number of items in this adapter: " + itemCount);
        return itemCount;
    }

    /**
     * Called by RecyclerView when it stops observing this Adapter.
     *
     * @param recyclerView The RecyclerView instance which stopped observing this adapter.
     * @see #onAttachedToRecyclerView(RecyclerView)
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        clearNewsArrayList();
    }

    @Override
    public void onItemClick(News item, View clickedView) {
        mListener.onItemClick(item, clickedView);
    }

    /* ----------- */
    /* VIEW HOLDER */
    /* ----------- */

    /**
     * The ViewHolder here is just a wrapper for MVC view.
     */
    class NewsListViewHolder extends RecyclerView.ViewHolder {
        private final NewsListItemViewMvc mViewMvc;

        /**
         * Constructor for our ViewHolder.
         *
         * @param itemView The View that we inflated in {@link NewsListAdapter#onCreateViewHolder}.
         */
        NewsListViewHolder(NewsListItemViewMvc itemView) {
            super(itemView.getRootView());
            mViewMvc = itemView;
        }
    }
}
