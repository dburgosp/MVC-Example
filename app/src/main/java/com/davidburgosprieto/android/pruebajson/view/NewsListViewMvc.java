package com.davidburgosprieto.android.pruebajson.view;

import android.view.View;

import com.davidburgosprieto.android.pruebajson.model.News;

import java.util.ArrayList;

public interface NewsListViewMvc {
    interface Listener {
        void onItemClick(News item, View clickedView);
    }

    void registerListener(Listener listener);

    void unregisterListener(Listener listener);

    View getRootView();

    void bindNewerNews(ArrayList<News> news);

    void bindOtherNews(ArrayList<News> news);

    void setProgress(boolean onOff);

    void setInfoMessage(int resourceId);
}
