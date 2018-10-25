package com.davidburgosprieto.android.pruebajson.view.dependencyinjection;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.davidburgosprieto.android.pruebajson.view.implementations.NewsListItemViewMvcImpl;
import com.davidburgosprieto.android.pruebajson.view.implementations.NewsListViewMvcImpl;
import com.davidburgosprieto.android.pruebajson.view.implementations.ToolbarViewMvcImpl;
import com.davidburgosprieto.android.pruebajson.view.implementations.WebViewMvcImpl;
import com.davidburgosprieto.android.pruebajson.view.interfaces.NewsListItemViewMvc;
import com.davidburgosprieto.android.pruebajson.view.interfaces.NewsListViewMvc;
import com.davidburgosprieto.android.pruebajson.view.interfaces.ToolbarViewMvc;
import com.davidburgosprieto.android.pruebajson.view.interfaces.WebViewMvc;

/**
 * Public class for dependency injection, used for instantiating other functional classes. It is
 * very related to the implementation of MVC Views in this app, so this class is not stored into the
 * "common.dependencyinjection" package but here, into the "view.dependencyinjection" package.
 */
public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;

    /* ************ */
    /* CONSTRUCTORS */
    /* ************ */

    /**
     * Constructor for objects of this class.
     *
     * @param layoutInflater is the LayoutInflater needed to instantiate MVC views.
     */
    public ViewMvcFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    /* ************** */
    /* PUBLIC METHODS */
    /* ************** */

    /**
     * Public method for migrating UI logic into the view and registering for receiving UI events
     * from the View class NewsListViewMvcImpl.
     *
     * @param parent is the parent ViewGroup.
     * @return the View class for representing a list of news.
     */
    public NewsListViewMvc getNewsListViewMvc(@Nullable ViewGroup parent) {
        return new NewsListViewMvcImpl(mLayoutInflater, parent, this);
    }

    /**
     * Public method for migrating UI logic into the view and registering for receiving UI events
     * from the View class NewsListItemViewMvcImpl.
     *
     * @param parent is the parent ViewGroup.
     * @return the View class for representing an element into the list of news.
     */
    public NewsListItemViewMvc getNewsListItemViewMvc(@Nullable ViewGroup parent) {
        return new NewsListItemViewMvcImpl(mLayoutInflater, parent);
    }

    /**
     * Public method for migrating UI logic into the view and register for receiving UI events from
     * the View class WebViewMvcImpl.
     *
     * @param parent is the parent ViewGroup.
     * @return the View class for showing a web page into a WebView.
     */
    public WebViewMvc getWebViewMvc(@Nullable ViewGroup parent) {
        return new WebViewMvcImpl(mLayoutInflater, parent, this);
    }

    /**
     * Public method for migrating UI logic into the view and register for receiving UI events from
     * the View class ToolbarViewMvcImpl.
     *
     * @param parent is the parent ViewGroup.
     * @return the View class for representing a Toolbar.
     */
    public ToolbarViewMvc getToolbarViewMvc(@Nullable ViewGroup parent) {
        return new ToolbarViewMvcImpl(mLayoutInflater, parent);
    }
}
