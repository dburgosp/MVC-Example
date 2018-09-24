package com.davidburgosprieto.android.pruebajson;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class NewsListAdapter
        extends RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder> {
    private static final String TAG = NewsListAdapter.class.getSimpleName();
    private final OnItemClickListener listener;
    private ArrayList<News> newsArrayList;

    /**
     * Constructor for this class.
     *
     * @param newsArrayList is the list of news that will be represented into the adapter.
     * @param listener      is the listener for receiving the clicks.
     */
    public NewsListAdapter(ArrayList<News> newsArrayList, OnItemClickListener listener) {
        this.newsArrayList = newsArrayList;
        this.listener = listener;
    }

    /**
     * Public helper method to clear the current news arrayList.
     */
    public void clearNewsArrayList() {
        this.newsArrayList.clear();
        notifyDataSetChanged();
    }

    /**
     * Setter method for updating the list of news in the adapter.
     *
     * @param newsArrayList is the new list of news.
     */
    public void updateNewsArrayList(ArrayList<News> newsArrayList) {
        this.newsArrayList.addAll(newsArrayList);
        notifyDataSetChanged();
    }

    /**
     * Called when RecyclerView needs a new {@link NewsListViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(NewsListViewHolder, int)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
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
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        Log.i(TAG + "." + methodName, "ViewHolder created");
        return new NewsListViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link NewsListViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link NewsListViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the
     *                   item at the given position in the data set.
     * @param position   The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(NewsListViewHolder viewHolder, int position) {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log.i(TAG + "." + methodName, "Displaying data at position " + position);
        if (!newsArrayList.isEmpty()) {
            // Update NewsListViewHolder with the movie movie_details_menu at current position in the adapter.
            News currentNews = newsArrayList.get(position);
            viewHolder.bind(currentNews, listener);
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
        int itemCount = newsArrayList.size();
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

    /**
     * Set a click listener to the RecyclerView, so we can manage OnClick events from the Main
     * Activity from which the RecyclerView is created.
     * <p>
     * For more information: https://antonioleiva.com/recyclerview-listener/
     */
    public interface OnItemClickListener {
        void onItemClick(News item, View clickedView);
    }

    /* ----------- */
    /* VIEW HOLDER */
    /* ----------- */

    class NewsListViewHolder extends RecyclerView.ViewHolder {
        private final String TAG = NewsListViewHolder.class.getSimpleName();

        private ImageView imageView;
        private TextView titleTextView, headerTextView, dateTextView, newsTickerTextView;

        private Context context;
        private View viewHolder;

        /**
         * Constructor for our ViewHolder.
         *
         * @param itemView The View that we inflated in {@link NewsListAdapter#onCreateViewHolder}.
         */
        NewsListViewHolder(View itemView) {
            super(itemView);
            
            // Get layout elements.
            imageView = (ImageView) itemView.findViewById(R.id.news_image);
            titleTextView = (TextView) itemView.findViewById(R.id.news_title);
            headerTextView = (TextView) itemView.findViewById(R.id.news_header);
            dateTextView = (TextView) itemView.findViewById(R.id.news_date);
            newsTickerTextView = (TextView) itemView.findViewById(R.id.news_ticker);

            context = itemView.getContext();
            viewHolder = itemView;
        }

        /**
         * Helper method for setting movie information for the current NewsListViewHolder
         * from the {@link NewsListAdapter#onBindViewHolder(NewsListViewHolder, int)}
         * method.
         *
         * @param currentNews is the News object attached to the current
         *                    NewsListViewHolder element.
         * @param listener    is the listener for click events.
         */
        public void bind(final News currentNews,
                         final NewsListAdapter.OnItemClickListener listener) {
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            Log.i(TAG + "." + methodName, "Binding data for the current NewsListViewHolder.");

            // News image.
            String image = currentNews.getImage();
            if (!image.equals("")) {
                // Display image using Picasso. If there is any error, show default image
                // R.drawable.no_backdrop.
                Picasso.with(context)
                        .load(image)
                        .error(R.drawable.no_backdrop)
                        .into(imageView);
            } else
                imageView.setVisibility(View.GONE);

            // News header.
            String header = currentNews.getHeader();
            if (!header.equals(""))
                headerTextView.setText(header);
            else
                headerTextView.setVisibility(View.GONE);

            // News title.
            String title = currentNews.getTitle();
            if (!title.equals(""))
                titleTextView.setText(title);
            else
                titleTextView.setVisibility(View.GONE);

            // News date.
            String date = DateTimeUtils.getStringFromDate(currentNews.getDate(),
                    DateTimeUtils.DATE_FORMAT_MEDIUM);
            if (!title.equals(""))
                dateTextView.setText(date);
            else
                dateTextView.setVisibility(View.GONE);

            // News ticker.
            String newsTicker = currentNews.getNewsTicker();
            if (!newsTicker.equals(""))
                newsTickerTextView.setText(newsTicker);
            else
                newsTickerTextView.setVisibility(View.GONE);

            // Set the listener for click events.
            viewHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(currentNews, viewHolder);
                }
            });
        }
    }
}
