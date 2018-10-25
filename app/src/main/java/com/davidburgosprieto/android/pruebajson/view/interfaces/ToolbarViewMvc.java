package com.davidburgosprieto.android.pruebajson.view.interfaces;

import android.view.View;

public interface ToolbarViewMvc {
    interface Listener {
        void onNavigateUpClicked();
    }

    View getRootView();

    void enableUpButtonAndListen(Listener navigateUpClickListener);

    void setTitle(String title);
}
