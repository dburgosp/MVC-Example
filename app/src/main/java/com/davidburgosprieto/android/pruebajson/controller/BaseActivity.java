package com.davidburgosprieto.android.pruebajson.controller;

import android.support.v7.app.AppCompatActivity;

import com.davidburgosprieto.android.pruebajson.common.CustomApplication;
import com.davidburgosprieto.android.pruebajson.common.dependencyinjection.ControllerCompositionRoot;

/**
 * Public class that every activity (MVC controller) here must extend. It uses dependency injection
 * to guarantee the segregation of application logic into Functional Set and Construction Set.
 */
public class BaseActivity
        extends AppCompatActivity {

    private ControllerCompositionRoot mControllerCompositionRoot;

    /**
     * Protected method for giving easy access to the global ControllerCompositionRoot.
     *
     * @return the global ControllerCompositionRoot.
     */
    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((CustomApplication) getApplication()).getCompositionRoot(),
                    this);
        }
        return mControllerCompositionRoot;
    }
}
