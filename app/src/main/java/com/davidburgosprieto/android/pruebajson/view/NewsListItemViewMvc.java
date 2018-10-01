package com.davidburgosprieto.android.pruebajson.view;

import android.view.View;

import com.davidburgosprieto.android.pruebajson.model.News;

public interface NewsListItemViewMvc {
    interface Listener {
        void onItemClick(News item, View clickedView);
    }

    void registerListener(Listener listener);

    void unregisterListener(Listener listener);

    View getRootView();

    void bindNews(News news);
}
