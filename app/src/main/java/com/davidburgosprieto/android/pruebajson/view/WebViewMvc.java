package com.davidburgosprieto.android.pruebajson.view;

import android.view.View;

import com.davidburgosprieto.android.pruebajson.model.News;

public interface WebViewMvc {
    View getRootView();

    void loadUrl(String url);
}
