package com.rickandmorty.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {

  private static final String SERVER_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; //2013-04-24T22:53:03.250Z
  public static final String DATE_MONTH_FULL_PATTERN = "dd MMMM yyyy";

  public static String convertServerTime(String dateTime, String convertPattern) {
    SimpleDateFormat sdf = new SimpleDateFormat(SERVER_DATE_TIME_PATTERN, java.util.Locale.getDefault());
    try {
      Date date = sdf.parse(dateTime);
      return convertServerTime(date, convertPattern);
    } catch (ParseException e) {
      return "";
    }
  }

  private static String convertServerTime(Date dateTime, String convertPattern) {
    Date fromGmt = new Date(dateTime.getTime() + TimeZone.getDefault().getOffset(dateTime.getTime()));
    SimpleDateFormat sdf2 = new SimpleDateFormat(convertPattern, java.util.Locale.getDefault());
    return sdf2.format(fromGmt);
  }

}
