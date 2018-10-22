package com.davidburgosprieto.android.pruebajson.common;

import android.content.Context;
import android.view.View;

/**
 * All ViewMvc classes must extend this abstract class.
 */
public abstract class BaseViewMvc implements ViewMvc {
    private View mRootView;

    @Override
    public View getRootView() {
        return mRootView;
    }

    /**
     * Setter method limited to subclasses, so it is protected, not public.
     *
     * @param rootView is the root view inflated from a specified XML node.
     */
    protected void setRootView(View rootView) {
        mRootView = rootView;
    }

    /**
     * Protected custom findViewById method.
     *
     * @param id is the resource identifier.
     * @return the view if found or null otherwise.
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id) {
        return (T) getRootView().findViewById(id);
    }

    /**
     * Protected custom getContext method.
     *
     * @return the context from the root view.
     */
    protected Context getContext() {
        return getRootView().getContext();
    }
}
