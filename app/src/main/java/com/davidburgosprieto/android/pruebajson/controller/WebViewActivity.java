package com.davidburgosprieto.android.pruebajson.controller;

import android.os.Bundle;

import com.davidburgosprieto.android.pruebajson.view.interfaces.WebViewMvc;

public class WebViewActivity
        extends BaseActivity
        implements WebViewMvc.Listener {

    private WebViewMvc mWebViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Migrate UI logic into the View class WebViewMvcImpl. Here we use dependency injection to
        // achieve this.
        mWebViewMvc = getCompositionRoot().getViewMvcFactory().getWebViewMvc(null);

        // Get parameter (url) from calling activity and load it into the WebView.
        String url = "http://www.marca.com/";
        if (getIntent().hasExtra("url"))
            url = getIntent().getStringExtra("url");
        mWebViewMvc.loadUrl(url);

        setContentView(mWebViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Register for listening UI events in the WebViewMvc.
        mWebViewMvc.registerListener(this);
    }


    @Override
    protected void onStop() {
        super.onStop();

        // Unregister for listening UI events in the WebViewMvc.
        mWebViewMvc.unregisterListener(this);
    }

    @Override
    public void onBackPressed() {
        if (!mWebViewMvc.canGoBack()) {
            // Navigate to previous activity and implicitly finish this one, only if there are no
            // more web pages to go up inside the WebView.
            super.onBackPressed();
        }
    }

    /**
     * Implementation of WebViewMvc.Listener.
     */
    @Override
    public void onNavigateUpClicked() {
        // When onNavigateUpClicked() UI event is triggered we call the overridden method
        // onBackPressed() in order to determine if whether navigating back to previous activity (if
        // there are no more web pages to go up inside the WebView) or not.
        onBackPressed();
    }
}
