package com.davidburgosprieto.android.pruebajson.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.davidburgosprieto.android.pruebajson.R;
import com.davidburgosprieto.android.pruebajson.model.News;
import com.davidburgosprieto.android.pruebajson.view.NewsListViewMvc;
import com.davidburgosprieto.android.pruebajson.view.NewsListViewMvcImpl;
import com.davidburgosprieto.android.pruebajson.view.WebViewMvc;
import com.davidburgosprieto.android.pruebajson.view.WebViewMvcImpl;

public class WebViewActivity extends AppCompatActivity {
    private WebViewMvc mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Migrate UI logic into the View class WebViewMvcImpl.
        mViewMvc = new WebViewMvcImpl(LayoutInflater.from(this), null);

        // Get parameter from calling activity.
        String url = "http://www.marca.com/";
        if (getIntent().hasExtra("url"))
            url = getIntent().getStringExtra("url");
        mViewMvc.loadUrl(url);
        setContentView(mViewMvc.getRootView());
    }
}
