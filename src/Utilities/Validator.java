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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jedidiah May
 */
public class Validator {

    // Checks to see if an array of text is empty
    public static boolean textIsEntered(String[] textFields) {

        for (String textField : textFields) {
            if (textField.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // Checks to see if two strings match each other
    // This is simple enough to not have a method, but there are future options available now (if other requirements are necessary)
    public static boolean stringDoMatch(String text1, String text2) {
        return text1.trim().equals(text2.trim());
    }
    
    // Determines if the search string is a number for code flow.
    public static boolean searchStringIsANumber(String search) {

        try {
            // Attempts to assign the string to an int variable. If there is an exception, it's not a number, and it falls through the catch.
            int aNumber = Integer.parseInt(search);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    // Uses Regex to determine if the time is in the correct format
    public static boolean timeIsInCorrectFormat(String time) {
        if (time == null || time.isEmpty())
            return true;
        
        String regex = "^[0-1][0-9]:[0-5][0-9]";
        Pattern pattern = Pattern.compile(regex);
        
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }

    // Ensures the date selected is not a weekend.
    public static boolean dateIsWeekday(DayOfWeek day) {
        return !(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);
    }
    
    // Ensures the date selected will take place in the future (shouldn't insert appointments prior today)
    public static boolean dateIsAfterCurrentDate(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
    
    // Ensures the date selected will take place in the future (shouldn't insert appointments prior today)
    public static boolean dateisInCorrectFormat(LocalDate date) {
        if (date ==  null) {
            return true;
        }
        
        String regex = "(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((18|19|20|21)\\\\d\\\\d)";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(date.toString());
        return matcher.matches();
    }

}
