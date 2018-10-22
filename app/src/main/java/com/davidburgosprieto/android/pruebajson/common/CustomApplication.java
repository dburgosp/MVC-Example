package com.davidburgosprieto.android.pruebajson.common;

import android.app.Application;

import com.davidburgosprieto.android.pruebajson.common.dependencyinjection.CompositionRoot;

public class CustomApplication extends Application {
    private CompositionRoot mCompositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        mCompositionRoot = new CompositionRoot();
    }

    /**
     * Getter method to expose the CompositionRoot object to other classes.
     *
     * @return the CompositionRoot.
     */
    public CompositionRoot getCompositionRoot() {
        return mCompositionRoot;
    }
}
