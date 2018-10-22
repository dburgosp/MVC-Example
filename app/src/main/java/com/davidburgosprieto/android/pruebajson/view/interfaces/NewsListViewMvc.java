package com.davidburgosprieto.android.pruebajson.view.interfaces;

import android.view.View;

import com.davidburgosprieto.android.pruebajson.common.ObservableViewMvc;
import com.davidburgosprieto.android.pruebajson.model.News;

import java.util.ArrayList;

public interface NewsListViewMvc extends ObservableViewMvc<NewsListViewMvc.Listener> {
    interface Listener {
        void onItemClick(News item, View clickedView);
    }

    void bindNewerNews(ArrayList<News> news);

    void bindOtherNews(ArrayList<News> news);

    void setProgress(boolean onOff);

    void setInfoMessage(int resourceId);
}
