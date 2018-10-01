package com.davidburgosprieto.android.pruebajson.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.davidburgosprieto.android.pruebajson.R;
import com.davidburgosprieto.android.pruebajson.model.News;

import java.util.ArrayList;

/**
 * View class for showing a webpage into a WebView. It contains UI Layer logic and sends notifications
 * about user interaction events through the "Decoupling Layer" Listener interface (in fact this
 * interface really belongs to the UI Layer) to any Controller classes that need this information
 * (at present, {@link com.davidburgosprieto.android.pruebajson.controller.NewsListActivity}).
 */
public class WebViewMvcImpl implements WebViewMvc {

    private WebView mWebView;
    private final View mRootView;

    /**
     * Constructor for this class.
     *
     * @param inflater is the LayoutInflater from the calling activity for instantiating layout XML
     *                 files into their corresponding View objects.
     * @param parent   is the root View of the generated hierarchy.
     */
    public WebViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        mRootView = inflater.inflate(R.layout.activity_web_view, parent, false);

        // Initialise the WebView.
        mWebView = findViewById(R.id.news_web_view);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public void loadUrl(String url) {
        mWebView.loadUrl(url);
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