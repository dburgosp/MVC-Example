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

    /* ************ */
    /* CONSTRUCTORS */
    /* ************ */

    /**
     * Constructor for this class.
     *
     * @param inflater is the LayoutInflater from the calling activity for instantiating layout XML
     *                 files into their corresponding View objects.
     * @param parent   is the root View of the generated hierarchy.
     */
    public ToolbarViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.toolbar_layout, parent, false));
        mTitleTextView = findViewById(R.id.toolbar_title_textview);
        mBackImageButton = findViewById(R.id.toolbar_back_button);

        // Set a listener on the "Navigate Back" button.
        mBackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onNavigateUpClicked();
            }
        });
    }

    /* ************** */
    /* PUBLIC METHODS */
    /* ************** */

    /**
     * Public method to be used from MVC controllers that instantiate objects of
     * {@link ToolbarViewMvcImpl} class for managing UI events on "Navigate Back" button.
     *
     * @param navigateUpClickListener is the Listener to be attached to the "Navigate Back" button.
     */
    @Override
    public void enableUpButtonAndListen(Listener navigateUpClickListener) {
        mListener = navigateUpClickListener;
        mBackImageButton.setVisibility(View.VISIBLE);
    }

    /**
     * Public method to be used from MVC controllers that instantiate objects of
     * {@link ToolbarViewMvcImpl} class for setting the Toolbar title.
     *
     * @param title is the text to be set as the Toolbar title.
     */
    @Override
    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }
}
