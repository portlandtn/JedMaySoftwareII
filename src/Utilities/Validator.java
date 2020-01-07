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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jedidiah May
 */
public class Validator {

    //Checks to see if an array of text is empty
    public static Boolean isTextEntered(String[] textFields) {

        for (String textField : textFields) {
            if (textField.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    //Checks to see if two strings match each other
    public static Boolean doStringsMatch(String text1, String text2) {
        return text1.trim().equals(text2.trim());
    }
    
    //Determines if the search string is a number for code flow.
    public static Boolean isSearchStringNumber(String search) {

        try {
            //Attempts to assign the string to an int variable. If there is an exception, it's not a number, and it falls through the catch.
            int aNumber = Integer.parseInt(search);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    public static Boolean isTimeInCorrectFormat(String time) {
        
        String regex = "^[0-2][0-9]:[0-5][0-9]";
        Pattern pattern = Pattern.compile(regex);
        
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }
    
    public static boolean isTimeWithinOperatingHours(String start, String end) {
        return !(LocalTime.parse(start + ":00").isBefore(DataProvider.OPENING_TIME) || LocalTime.parse(end + ":00").isAfter(DataProvider.CLOSING_TIME));
    }
    
    public static boolean isStartTimeBeforeEndTime(String start, String end) {
        return !(LocalTime.parse(end + ":00").isBefore(LocalTime.parse(start + ":00")));
    }

    public static boolean isDateSelectedAWeekday(DayOfWeek date) {
        return !(date == DayOfWeek.SATURDAY || date == DayOfWeek.SUNDAY);
    }
    
    public static boolean isDateSelectedAfterToday(LocalDate date) {
        return !(date.isAfter(LocalDate.now()));
    }

}
