package com.davidburgosprieto.android.pruebajson.view.interfaces;

import android.view.View;

import com.davidburgosprieto.android.pruebajson.common.ObservableViewMvc;
import com.davidburgosprieto.android.pruebajson.model.News;

public interface NewsListItemViewMvc extends ObservableViewMvc<NewsListItemViewMvc.Listener> {
    interface Listener {
        void onItemClick(News item, View clickedView);
    }

    void bindNews(News news);
}
