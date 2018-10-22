package com.davidburgosprieto.android.pruebajson.view.interfaces;

public interface ToolbarViewMvc {
    interface Listener {
        void onNavigateUpClicked();
    }

    void setTitle(String title);
}
