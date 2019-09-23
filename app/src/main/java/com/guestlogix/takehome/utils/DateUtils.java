package com.guestlogix.takehome.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static Date parseDate(String timestamp) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        try {
            return df.parse(timestamp);
        } catch (ParseException e) {
            return null;
        }
    }
}
