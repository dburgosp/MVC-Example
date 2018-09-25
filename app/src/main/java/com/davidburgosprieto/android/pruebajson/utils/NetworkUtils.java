package com.davidburgosprieto.android.pruebajson.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {
    private final static String TAG = NetworkUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link NetworkUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name NetworkUtils (and an object instance of NetworkUtils is not
     * needed).
     */
    private NetworkUtils() {
    }

    /**
     * Helper method to determine if there is an active network connection.
     *
     * @return true if we are connected to the internet, false otherwise.
     */
    public static boolean isConnected(Context context) {
        String methodTAG = TAG + "." + Thread.currentThread().getStackTrace()[2].getMethodName();

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        try {
            activeNetwork = cm.getActiveNetworkInfo();
        } catch (NullPointerException e) {
            Log.e(methodTAG, "Error getting active network info: " + e);
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Private helper method for building a Url from a given Uri.
     *
     * @param builtUri is the given Uri.
     * @return the Url built from the given Uri.
     */
    public static URL buildUrlFromUri(Uri builtUri) {
        String methodTAG = TAG + "." + Thread.currentThread().getStackTrace()[2].getMethodName();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.i(methodTAG, "Built URL: " + url);
        } catch (MalformedURLException e) {
            Log.e(methodTAG, "Error building URL: " + e);
        }
        return url;
    }

    /**
     * Retrieves a JSON document from an Uri.
     *
     * @param builtUri is the uniform resource identifier (uri) for retrieving the JSON document.
     * @return a String with the JSON document.
     * @throws java.io.IOException from url.openConnection().
     */
    public static String getJSONresponse(Uri builtUri) throws java.io.IOException {
        String methodTAG = TAG + "." + Thread.currentThread().getStackTrace()[2].getMethodName();

        URL url = buildUrlFromUri(builtUri);
        Log.i(methodTAG, "URL: " + url);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
