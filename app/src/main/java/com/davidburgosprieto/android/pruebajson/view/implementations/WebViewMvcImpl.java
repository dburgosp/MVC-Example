package com.davidburgosprieto.android.pruebajson.view.implementations;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.widget.Toolbar;

import com.davidburgosprieto.android.pruebajson.R;
import com.davidburgosprieto.android.pruebajson.common.BaseObservableViewMvc;
import com.davidburgosprieto.android.pruebajson.view.ViewMvcFactory;
import com.davidburgosprieto.android.pruebajson.view.interfaces.WebViewMvc;

/**
 * View class for showing a webpage into a WebView. It contains UI Layer logic and sends notifications
 * about user interaction events through the "Decoupling Layer" Listener interface (in fact this
 * interface really belongs to the UI Layer) to any Controller classes that need this information
 * (at present, {@link com.davidburgosprieto.android.pruebajson.controller.NewsListActivity}).
 */
public class WebViewMvcImpl
        extends BaseObservableViewMvc<WebViewMvc.Listener>
        implements WebViewMvc {

    private WebView mWebView;
    private final Toolbar mToolbar;
    private final ToolbarViewMvcImpl mToolbarViewMvc;

    /**
     * Constructor for this class.
     *
     * @param inflater is the LayoutInflater from the calling activity for instantiating layout XML
     *                 files into their corresponding View objects.
     * @param parent   is the root View of the generated hierarchy.
     */
    public WebViewMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        // Use the setRootView method from the BaseViewMvc abstract class.
        setRootView(inflater.inflate(R.layout.activity_web_view, parent, false));

        // Set Toolbar (nested MVC View).
        mToolbar = findViewById(R.id.toolbar);
        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);
        initToolbar();

        // Initialise the WebView.
        mWebView = findViewById(R.id.news_web_view);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
    }

    private void initToolbar() {
        mToolbar.addView(mToolbarViewMvc.getRootView());
        mToolbarViewMvc.setTitle(getContext().getString(R.string.app_name));
        mToolbarViewMvc.enableUpButtonAndListen(new ToolbarViewMvcImpl.Listener() {
            @Override
            public void onNavigateUpClicked() {
                for (Listener listener : getListeners()) {
                    listener.onNavigateUpClicked();
                }
            }
        });
    }

    @Override
    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }
}