package com.davidburgosprieto.android.pruebajson.common;

/**
 * Interface of the Observer Design Pattern, for using ViewMvc from Views that need to manage UI
 * interactions.
 *
 * @param <ListenerType>
 */
public interface ObservableViewMvc<ListenerType> extends ViewMvc {
    void registerListener(ListenerType listener);

    void unregisterListener(ListenerType listener);
}
