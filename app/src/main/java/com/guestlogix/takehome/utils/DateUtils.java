package com.guestlogix.takehome.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String DISPLAY_DATE_FORMAT = "MMM dd, yyyy HH:mm";

    public static Date parseDate(String timestamp) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        try {
            return df.parse(timestamp);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getFormattedDate(String timestamp) {

        Date date = parseDate(timestamp);
        SimpleDateFormat df = new SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.US);

        if(date != null) {
            return df.format(date);
        }

        return "";
    }
}
