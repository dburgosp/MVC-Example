package com.davidburgosprieto.android.pruebajson.common;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * All ViewMvc classes that need to receive UI changes must extend this abstract class.
 */
public abstract class BaseObservableViewMvc<ListenerType> extends BaseViewMvc
        implements ObservableViewMvc<ListenerType> {

    private Set<ListenerType> mListeners = new HashSet<>();

    @Override
    public void registerListener(ListenerType listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregisterListener(ListenerType listener) {
        mListeners.remove(listener);
    }

    /**
     * Protected method to grant access for the subclasses to the listeners set.
     *
     * @return the listeners set.
     */
    protected Set<ListenerType> getListeners() {
        // Implementations of the MVC Views should not modify the list of listeners obtained from
        // this method, so we put a fail fast mechanism in place to prevent this invalid operation
        // and let the future maintainers know about their mistake right away.
        return Collections.unmodifiableSet(mListeners);
    }
}
