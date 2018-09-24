package com.davidburgosprieto.android.pruebajson;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Public class with static resources for fetching data from OMDB.
 */
public class NewsJSONUtils {
    private final static String TAG = NewsJSONUtils.class.getSimpleName();

    // URL.
    private final static String BASE_URL = "https://api.myjson.com/bins/8p7vg";

    /**
     * Fetches BASE_URL for a list of news.
     *
     * @return an array of {@link News} objects.
     */
    public static ArrayList<News> getNews() {
        String methodTag = TAG + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
        ArrayList<News> news = new ArrayList<>();

        /* ------------ */
        /* Get the JSON */
        /* ------------ */

        // Build the uniform resource identifier (uri) for fetching data.
        Uri builtUri = Uri.parse(BASE_URL).buildUpon().build();

        // Use the built uri to get the JSON document with the results of the query.
        String responseJSONString;
        try {
            responseJSONString = NetworkUtils.getJSONresponse(builtUri);
        } catch (java.io.IOException e) {
            // If getJSONresponse has thrown an exception, exit returning null.
            Log.e(methodTag, "Error retrieving JSON response: ", e);
            return null;
        }

        /* -------------- */
        /* Parse the JSON */
        /* -------------- */

        // If there is no data to parse, exit returning null.
        if (android.text.TextUtils.isEmpty(responseJSONString)) {
            return null;
        }

        // Try to parse the JSON response string. If there's a problem with the way the JSON is
        // formatted, a JSONException exception object will be thrown.
        try {
            // Create a JSONObject from the JSON response string.
            JSONObject responseJSONObject = new JSONObject(responseJSONString);

            // If there is a "cts" section, create a new JSONArray for parsing results.
            if (!responseJSONObject.isNull("cts")) {
                JSONArray ctsJSONArray = responseJSONObject.getJSONArray("cts");
                JSONObject ctsJSONObject;
                for (int n = 0; n < ctsJSONArray.length(); n++) {
                    // Get a single result at position n within the list of results.
                    ctsJSONObject = ctsJSONArray.getJSONObject(n);

                    // Extract the required values from the corresponding keys.
                    String url = getStringFromJSON(ctsJSONObject, "url");
                    String title = getStringFromJSON(ctsJSONObject, "titulo");
                    String newsTicker = getStringFromJSON(ctsJSONObject, "cintillo");
                    String stringDate = getStringFromJSON(ctsJSONObject, "publishedAt");
                    String header = getStringFromJSON(ctsJSONObject, "antetitulo");
                    String image = "";
                    int imageHeight = 0;
                    int imageWidth = 0;

                    // Get "multimedia" array, if it exists, and get only the first element of type
                    // "image".
                    if (!ctsJSONObject.isNull("multimedia")) {
                        JSONArray multimediaJSONArray =
                                ctsJSONObject.getJSONArray("multimedia");
                        JSONObject multimediaJSONObject;
                        int i = 0;
                        boolean firstImageFound = false;
                        do {
                            multimediaJSONObject = multimediaJSONArray.getJSONObject(i);
                            if (!multimediaJSONObject.isNull("type")) {
                                String type = getStringFromJSON(multimediaJSONObject, "type");
                                if (type.equals("image")) {
                                    // First image found. No need to keep on going through the 
                                    // array.
                                    image = getStringFromJSON(multimediaJSONObject, "url");
                                    imageWidth = getIntFromJSON(multimediaJSONObject, "width");
                                    imageHeight = getIntFromJSON(multimediaJSONObject, "height");
                                    firstImageFound = true;
                                }
                            }
                            i++;
                        } while (i <= multimediaJSONArray.length() && !firstImageFound);
                    }

                    // Create the News object and add it to the News array.
                    Date date = DateTimeUtils.getDateFromString(stringDate);
                    News currentNews = new News(title, newsTicker, date, header, image, imageHeight,
                            imageWidth, url);
                    news.add(currentNews);
                }
            }

            // Return the {@link News} array with the required data retrieved from the JSON 
            // response.
            return news;
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash.
            Log.e(methodTag, "Error parsing the JSON response: ", e);
            return null;
        }
    }

    /**
     * Helper method to get the String value of a key into a JSON object.
     *
     * @param jsonObject is the JSON object that is being parsed.
     * @param key        is the key
     *                   to search into the JSON object.
     * @return the String value associated to the given key, or an empty String if the key does not
     * exist.
     */
    static public String getStringFromJSON(JSONObject jsonObject, String key) {
        String methodTAG = TAG + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
        String value = "";

        if (!jsonObject.isNull(key)) try {
            value = jsonObject.getString(key);
            Log.e(methodTAG, "Key: " + key + " - Value: " + value);
        } catch (JSONException e) {
            // If an error is thrown when executing the "try" block, catch the exception here, so
            // the app doesn't crash. Print a log message with the message from the exception.
            Log.e(methodTAG, "Error parsing JSON response: ", e);
        }
        return value;
    }

    /**
     * Helper method to get the int value of a key into a JSON object.
     *
     * @param jsonObject is the JSON object that is being parsed.
     * @param key        is the key to search into the JSON object.
     * @return the int value associated to the given key, or 0 if the key does not exist.
     */
    static public int getIntFromJSON(JSONObject jsonObject, String key) {
        String methodTAG = TAG + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
        int value = 0;

        if (!jsonObject.isNull(key)) try {
            value = jsonObject.getInt(key);
            Log.e(methodTAG, "Key: " + key + " - Value: " + value);
        } catch (JSONException e) {
            // If an error is thrown when executing the "try" block, catch the exception here, so
            // the app doesn't crash. Print a log message with the message from the exception.
            Log.e(methodTAG, "Error parsing JSON response: ", e);
        }
        return value;
    }

    /**
     * Helper method to get the boolean value of a key into a JSON object.
     *
     * @param jsonObject is the JSON object that is being parsed.
     * @param key        is the key to search into the JSON object.
     * @return the boolean value associated to the given key, or false if the key does not exist.
     */
    static public boolean getBooleanFromJSON(JSONObject jsonObject, String key) {
        String methodTAG = TAG + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
        boolean value = false;

        if (!jsonObject.isNull(key)) try {
            value = jsonObject.getBoolean(key);
            Log.e(methodTAG, "Key: " + key + " - Value: " + value);
        } catch (JSONException e) {
            // If an error is thrown when executing the "try" block, catch the exception here, so
            // the app doesn't crash. Print a log message with the message from the exception.
            Log.e(methodTAG, "Error parsing JSON response: ", e);
        }
        return value;
    }

    /**
     * Helper method to get the Double value of a key into a JSON object.
     *
     * @param jsonObject is the JSON object that is being parsed.
     * @param key        is the key to search into the JSON object.
     * @return the Double value associated to the given key, or 0.0 if the key does not exist.
     */
    static public Double getDoubleFromJSON(JSONObject jsonObject, String key) {
        String methodTAG = TAG + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
        Double value = 0.0;

        if (!jsonObject.isNull(key)) try {
            value = jsonObject.getDouble(key);
            Log.e(methodTAG, "Key: " + key + " - Value: " + value);
        } catch (JSONException e) {
            // If an error is thrown when executing the "try" block, catch the exception here, so
            // the app doesn't crash. Print a log message with the message from the exception.
            Log.e(methodTAG, "Error parsing JSON response: ", e);
        }
        return value;
    }
}
