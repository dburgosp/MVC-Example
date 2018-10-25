package com.davidburgosprieto.android.pruebajson.view.interfaces;

import android.view.View;

import com.davidburgosprieto.android.pruebajson.common.ObservableViewMvc;

public interface WebViewMvc extends ObservableViewMvc<WebViewMvc.Listener> {
    interface Listener {
        void onNavigateUpClicked();
    }

    View getRootView();

    void loadUrl(String url);

    boolean canGoBack();
}
