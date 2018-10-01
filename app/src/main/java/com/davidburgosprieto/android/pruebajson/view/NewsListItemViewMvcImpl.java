package com.davidburgosprieto.android.pruebajson.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidburgosprieto.android.pruebajson.R;
import com.davidburgosprieto.android.pruebajson.model.News;
import com.davidburgosprieto.android.pruebajson.utils.DateTimeUtils;
import com.davidburgosprieto.android.pruebajson.model.NewsViewUtils;

import java.util.ArrayList;

/**
 * View class for representing a news element in the news list. It contains UI Layer logic and sends
 * notifications about user interaction events through the "Decoupling Layer" Listener interface (in
 * fact this interface really belongs to the UI Layer) to any Controller classes that need this
 * information (at present,
 * {@link com.davidburgosprieto.android.pruebajson.controller.NewsListAdapter}).
 */
public class NewsListItemViewMvcImpl implements NewsListItemViewMvc {
    private View mRootView;
    private final ArrayList<Listener> mListeners = new ArrayList<>();
    private News mNews;
    private ImageView mImageView;
    private TextView mTitleTextView, mHeaderTextView, mDateTextView;

    /**
     * Constructor for this class.
     *
     * @param inflater is the LayoutInflater from the calling activity for instantiating layout XML
     *                 files into their corresponding View objects.
     * @param parent   is the root View of the generated hierarchy.
     */
    public NewsListItemViewMvcImpl(LayoutInflater inflater, final ViewGroup parent) {
        mRootView = inflater.inflate(R.layout.list_item, parent, false);

        // Initialise layout elements.
        mImageView = findViewById(R.id.news_image);
        mTitleTextView = findViewById(R.id.news_title);
        mHeaderTextView = findViewById(R.id.news_header);
        mDateTextView = findViewById(R.id.news_date);

        // Set a click listener for the entire root view.
        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Listener listener : mListeners) {
                    listener.onItemClick(mNews, parent);
                }
            }
        });
    }

    @Override
    public void registerListener(Listener listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregisterListener(Listener listener) {
        mListeners.remove(listener);
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public void bindNews(News news) {
        mNews = news;

        // News image.
        NewsViewUtils.setNewsImage(getRootView().getContext(), mImageView, mNews.getImage(),
                mNews.getImageHeight(), mNews.getImageWidth(), false);

        // News ticker and header.
        NewsViewUtils.setNewsTickerAndHeader(mHeaderTextView, mNews.getNewsTicker(),
                mNews.getHeader());

        // News title.
        NewsViewUtils.setText(mTitleTextView, mNews.getTitle());

        // News date.
        NewsViewUtils.setText(mDateTextView, DateTimeUtils.getStringFromDate(mNews.getDate(),
                DateTimeUtils.DATE_FORMAT_MEDIUM));
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
