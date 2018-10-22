package com.davidburgosprieto.android.pruebajson.common.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateTimeUtils {
    private static final String TAG = DateTimeUtils.class.getSimpleName();

    // Public date formats.
    public static final int DATE_FORMAT_FULL = DateFormat.FULL;
    public static final int DATE_FORMAT_LONG = DateFormat.LONG;
    public static final int DATE_FORMAT_SHORT = DateFormat.SHORT;
    public static final int DATE_FORMAT_MEDIUM = DateFormat.MEDIUM;

    /**
     * Helper method to convert a string to a date.
     *
     * @param inputDate is the original string date, in "EEE, dd MMM yyyy HH:mm:ss Z" format.
     * @return the corresponding date.
     */
    public static Date getDateFromString(String inputDate) {
        String methodTag = TAG + "." + Thread.currentThread().getStackTrace()[2].getMethodName();

        // Parse input date string.
        Date date = new Date();
        SimpleDateFormat dateFormatIn;
        try {
            // Try given format.
            dateFormatIn = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
            date = dateFormatIn.parse(inputDate);
            return date;
        } catch (java.text.ParseException e) {
            // Return null if we can't parse the input date.
            Log.e(methodTag, "Error parsing date: " + e);
            return null;
        }
    }

    /**
     * Helper method to convert a date to a string.
     *
     * @param inputDate  is the original date.
     * @param dateFormat is the date format to be applied. Available values are
     *                   {@link DateTimeUtils#DATE_FORMAT_FULL},
     *                   {@link DateTimeUtils#DATE_FORMAT_LONG},
     *                   {@link DateTimeUtils#DATE_FORMAT_SHORT},
     *                   {@link DateTimeUtils#DATE_FORMAT_MEDIUM}
     * @return a string containing the formatted date.
     */
    public static String getStringFromDate(Date inputDate, int dateFormat) {
        // Format output date string.
        DateFormat dateFormatOut = DateFormat.getDateTimeInstance(dateFormat, DateFormat.SHORT,
                Locale.getDefault());
        return dateFormatOut.format(inputDate);
    }
}
