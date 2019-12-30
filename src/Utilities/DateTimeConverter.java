/*
 * Copyright (C) 2019 Jedidiah May.
 *
 * This program is free software and part of an academic course of study
 * with Western Governor's University. This program is stored on my
 * personal git accounts so that I can collaborate from multiple computers
 * easily. If you find this, feel free to use it for general concept and
 * as a guidepost for your own coursework.
 *
 * This program is distributed in the hope that it will be useful,
 * but please do not copy any of the code verbatim without first
 * understanding how it works. If you're a student, I wish you the best
 * and hope this is of value to you. If you're not a student, I hope you
 * enjoy irregardless.
 *
 * Look for other projects on my github account at <https://github.com/portlandtn/>.
 */
package Utilities;

import java.util.TimeZone;

/**
 *
 * @author Jedidiah May
 */
public class DateTimeConverter {

    public static String currentTimeZoneId;

    public static void convertToGMT() {
        currentTimeZoneId = TimeZone.getDefault().getID();
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

    public static void convertFromGMTToCurrent() {
        TimeZone.setDefault(TimeZone.getTimeZone(currentTimeZoneId));
    }

    public static String getHourFromTextField(String time, String ampm) {
        
        int ind = time.indexOf(":");
        if (ampm.equals("AM"))
            return time.substring(0, ind);
        else return time.substring(0, ind) + 12;
    }

    public static String getMinuteFromTextField(String time) {
        int ind = time.indexOf(":");
        return time.substring(ind + 1, time.length());
    }

}
