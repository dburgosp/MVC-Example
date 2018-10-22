package com.davidburgosprieto.android.pruebajson.common.utils;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidburgosprieto.android.pruebajson.R;
import com.squareup.picasso.Picasso;

public class NewsViewUtils {
    /**
     * Empty constructor.
     */
    public NewsViewUtils() {
    }

    /**
     * Public static method for loading an image url into an ImageView using Picasso.
     *
     * @param context    is the Context of the calling activity.
     * @param imageView  is the ImageView in which we are loading the image.
     * @param imageUrl   is the url of the image to be loaded.
     * @param height     is the image height.
     * @param width      is the image width.
     * @param fullScreen is true if the image must be displayed in full width, and false if the
     *                   image must be displayed using the fixed sizes of the ImageView.
     */
    public static void setNewsImage(Context context, ImageView imageView, String imageUrl,
                                    int height, int width, boolean fullScreen) {
        if (!imageUrl.equals("")) {
            // Display image using Picasso. If there is any error, show default image
            // R.drawable.no_backdrop.
            if (fullScreen) {
                // Calculate image size for scaleType="centerCrop", depending on display size and
                // original image width and height.
                final DisplayUtils displayUtils = new DisplayUtils(context);
                int widthPixels = displayUtils.getFullDisplayBackdropWidthPixels();
                int heightPixels = (widthPixels * height / width);

                // Display image using Picasso using custom image height and width. If there is any
                // error, show default image R.drawable.no_backdrop.
                Picasso.with(context)
                        .load(imageUrl)
                        .resize(widthPixels, heightPixels)
                        .centerCrop()
                        .error(R.drawable.no_backdrop)
                        .into(imageView);
            } else
                // Display image using Picasso using the predefined sizes for imageView. If there is
                // any error, show default image R.drawable.no_backdrop.
                Picasso.with(context)
                        .load(imageUrl)
                        .error(R.drawable.no_backdrop)
                        .into(imageView);
        } else
            imageView.setVisibility(View.GONE);
    }

    /**
     * Public static method for setting the text of the news ticker and header using HTML format.
     *
     * @param textView   is the TextView in which we are writing the text.
     * @param newsTicker is the text for the news ticker.
     * @param header     is the text for the news header.
     */
    public static void setNewsTickerAndHeader(TextView textView, String newsTicker, String header) {
        String htmlText = "";
        if (!newsTicker.equals("")) {
            htmlText = "<strong>" + newsTicker.toUpperCase() + "</strong>";
            if (!header.equals(""))
                htmlText = htmlText + " · " + header;
        } else if (!header.equals(""))
            htmlText = header;
        if (!htmlText.equals(""))
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                textView.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
            } else {
                textView.setText(Html.fromHtml(htmlText));
            }
        else
            textView.setVisibility(View.GONE);
    }

    /**
     * Public static method for writing a text in a TextView.
     *
     * @param textView is the TextView in which we are writing the text.
     * @param text     is the text to be written.
     */
    public static void setText(TextView textView, String text) {
        if (!text.equals(""))
            textView.setText(text);
        else
            textView.setVisibility(View.GONE);
    }
}
