package com.davidburgosprieto.android.pruebajson.common.dependencyinjection;

/**
 * Public class, belonging to the Construction Set. We need this to be able to have global services
 * that are shared among all clients.
 *
 * instantiate objects for Functional Set and
 * expose them to the outside world. It is tied to Application Lifecycle, so we can't construct
 * objects that depend on the Activity inside this class. Therefore we will use the
 * {@link ControllerCompositionRoot} instead to manage Activities contexts.
 */
public class CompositionRoot {
}
