package com.davidburgosprieto.android.pruebajson.common.dependencyinjection;

import android.app.Activity;
import android.view.LayoutInflater;

import com.davidburgosprieto.android.pruebajson.model.FetchNewsListUseCase;
import com.davidburgosprieto.android.pruebajson.view.dependencyinjection.ViewMvcFactory;

/**
 * Public class, belonging to the Construction Set, that resolves dependencies and instantiate
 * objects from Functional Set. It is tied to activity lifecycle and we need this to put Activity
 * Context on the objects graph.
 */
public class ControllerCompositionRoot {
    private final CompositionRoot mCompositionRoot;
    private final Activity mActivity;

    public ControllerCompositionRoot(CompositionRoot compositionRoot, Activity activity) {
        mCompositionRoot = compositionRoot;
        mActivity = activity;
    }

    /**
     * Public method to expose LayoutInflater for instantiating MVC views.
     *
     * @return the LayoutInflater from the Activity.
     */
    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    /**
     * Public method to expose the ViewMvcFactory class to the clients.
     *
     * @return the ViewMvcFactory constructed with the LayoutInflater from the Activity.
     */
    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(getLayoutInflater());
    }

    /**
     * Public method to expose the FetchNewsListUseCase class to the clients.
     *
     * @return the FetchNewsListUseCase constructed from the Activity.
     */
    public FetchNewsListUseCase getNewsAsyncTaskViewUseCase() {
        return new FetchNewsListUseCase(mActivity);
    }
}
