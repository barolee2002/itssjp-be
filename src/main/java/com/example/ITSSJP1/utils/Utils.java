package com.example.ITSSJP1.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getDate(String formatDate) {
        try {
            DateFormat formatter = new SimpleDateFormat(formatDate);
            return formatter.format(new Date());
        } catch (Exception e) {
            DateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT);
            return formatter.format(new Date());
        }
    }

    public static boolean isEmptyOrNull(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isCorrectFormat(String value, String format) {
        DateFormat formatter = new SimpleDateFormat(format);
        try {
            Date parsedDate = formatter.parse(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
