package com.davidburgosprieto.android.pruebajson.view.implementations;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.widget.Toolbar;

import com.davidburgosprieto.android.pruebajson.R;
import com.davidburgosprieto.android.pruebajson.common.BaseObservableViewMvc;
import com.davidburgosprieto.android.pruebajson.view.dependencyinjection.ViewMvcFactory;
import com.davidburgosprieto.android.pruebajson.view.interfaces.ToolbarViewMvc;
import com.davidburgosprieto.android.pruebajson.view.interfaces.WebViewMvc;

/**
 * View class for showing a web page into a WebView.
 * It contains UI Layer logic and sends notifications about user interaction events through the
 * "Decoupling Layer" Listener interface (in fact this interface really belongs to the UI Layer) to
 * any Controller classes that need this information (at present,
 * {@link com.davidburgosprieto.android.pruebajson.controller.NewsListActivity}).
 */
public class WebViewMvcImpl
        extends BaseObservableViewMvc<WebViewMvc.Listener>
        implements WebViewMvc {

    private WebView mWebView;
    private Toolbar mToolbar;
    private ToolbarViewMvc mToolbarViewMvc;

    /* ************ */
    /* CONSTRUCTORS */
    /* ************ */

    /**
     * Constructor for this class.
     *
     * @param inflater       is the LayoutInflater from the calling activity for instantiating
     *                       layout XML files into their corresponding View objects.
     * @param parent         is the root View of the generated hierarchy.
     * @param viewMvcFactory is the ViewMvcFactory for dependency injection, used for instantiating
     *                       other functional classes.
     */
    @SuppressLint("SetJavaScriptEnabled")
    public WebViewMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        // Use the setRootView method from the BaseViewMvc abstract class.
        setRootView(inflater.inflate(R.layout.activity_web_view, parent, false));

        // Get Toolbar (nested MVC View) and WebView.
        mWebView = findViewById(R.id.news_web_view);
        mToolbar = findViewById(R.id.toolbar);
        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);

        // Init layout elements.
        initToolbar();
        initWebView();
    }

    /* *************** */
    /* PRIVATE METHODS */
    /* *************** */

    /**
     * Private helper method for initialising the Toolbar.
     */
    private void initToolbar() {
        mToolbar.addView(mToolbarViewMvc.getRootView());
        mToolbarViewMvc.setTitle(getContext().getString(R.string.app_name));
        mToolbarViewMvc.enableUpButtonAndListen(new ToolbarViewMvcImpl.Listener() {
            @Override
            public void onNavigateUpClicked() {
                // Notify all listeners that the back button in the Toolbar has been clicked.
                for (Listener listener : getListeners()) {
                    listener.onNavigateUpClicked();
                }
            }
        });
    }

    /**
     * Private helper method for initialising the WebView.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
    }

    /* ************** */
    /* PUBLIC METHODS */
    /* ************** */

    /**
     * Public method to be used from MVC controllers that instantiate objects of
     * {@link WebViewMvcImpl} class for setting an URL to the WebView.
     *
     * @param url is the URL to be loaded into the WebView.
     */
    @Override
    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    /**
     * Public method to be used from MVC controllers that instantiate objects of
     * {@link WebViewMvcImpl} class for setting an URL to the WebView.
     */
    @Override
    public boolean canGoBack() {
        if (mWebView.canGoBack()) {
            // Navigate back inside the WebView context, if possible.
            mWebView.goBack();
            return true;
        } else {
            // We are at the top level web page, so we can't navigate up any more.
            return false;
        }
    }
}