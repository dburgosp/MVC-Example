package com.davidburgosprieto.android.pruebajson.view.implementations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidburgosprieto.android.pruebajson.R;
import com.davidburgosprieto.android.pruebajson.common.BaseObservableViewMvc;
import com.davidburgosprieto.android.pruebajson.common.utils.DateTimeUtils;
import com.davidburgosprieto.android.pruebajson.model.News;
import com.davidburgosprieto.android.pruebajson.view.interfaces.NewsListItemViewMvc;
import com.davidburgosprieto.android.pruebajson.common.utils.NewsViewUtils;

/**
 * View class for representing a news element in the news list. It contains UI Layer logic and sends
 * notifications about user interaction events through the "Decoupling Layer" Listener interface (in
 * fact this interface really belongs to the UI Layer) to any Controller classes that need this
 * information (at present,
 * {@link com.davidburgosprieto.android.pruebajson.controller.NewsListAdapter}).
 */
public class NewsListItemViewMvcImpl
        extends BaseObservableViewMvc<NewsListItemViewMvc.Listener>
        implements NewsListItemViewMvc {

    private News mNews;
    private ImageView mImageView;
    private TextView mTitleTextView, mHeaderTextView, mDateTextView;

    /* ************ */
    /* CONSTRUCTORS */
    /* ************ */

    /**
     * Constructor for this class.
     *
     * @param inflater is the LayoutInflater from the calling activity for instantiating layout XML
     *                 files into their corresponding View objects.
     * @param parent   is the root View of the generated hierarchy.
     */
    public NewsListItemViewMvcImpl(LayoutInflater inflater, final ViewGroup parent) {
        // Use the setRootView method from the BaseViewMvc abstract class.
        setRootView(inflater.inflate(R.layout.list_item, parent, false));

        // Get layout elements.
        mImageView = findViewById(R.id.news_image);
        mTitleTextView = findViewById(R.id.news_title);
        mHeaderTextView = findViewById(R.id.news_header);
        mDateTextView = findViewById(R.id.news_date);

        // Set a click listener for the entire root view. We use the getListeners() method from the
        // BaseObservableViewMvc abstract class to get the list of listeners.
        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Listener listener : getListeners()) {
                    listener.onItemClick(mNews, parent);
                }
            }
        });
    }

    /* ************** */
    /* PUBLIC METHODS */
    /* ************** */

    /**
     * Public method to be used from MVC controllers that instantiate objects of
     * {@link NewsListItemViewMvcImpl} class for binding a {@link News} object to the current list
     * item.
     *
     * @param news is the {@link News} object to be bound to the current list item.
     */
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
}
