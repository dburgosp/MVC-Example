package com.davidburgosprieto.android.pruebajson.view.implementations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.davidburgosprieto.android.pruebajson.R;
import com.davidburgosprieto.android.pruebajson.common.BaseObservableViewMvc;
import com.davidburgosprieto.android.pruebajson.view.interfaces.ToolbarViewMvc;

public class ToolbarViewMvcImpl
        extends BaseObservableViewMvc<ToolbarViewMvc.Listener>
        implements ToolbarViewMvc {

    private final TextView mTitleTextView;
    private final ImageButton mBackImageButton;
    private Listener mListener;

    public ToolbarViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.toolbar_layout, parent, false));
        mTitleTextView = findViewById(R.id.toolbar_title_textview);
        mBackImageButton = findViewById(R.id.toolbar_back_button);
        mBackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onNavigateUpClicked();
            }
        });
    }

    public void enableUpButtonAndListen(Listener navigateUpClickListener) {
        mListener = navigateUpClickListener;
        mBackImageButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }
}
