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
package Log;

import Utilities.DataProvider;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 *
 * @author Jedidiah May
 */
public class Logger {

    public static void logUserLogin() throws IOException {

        String filename = "src/Log/userLogins.txt";
        FileWriter fw = new FileWriter(filename, true);

        // Create and open file
        try (PrintWriter logFile = new PrintWriter(fw)) {
            logFile.println("User: " + DataProvider.getCurrentUser());
            logFile.println("Time: " + Calendar.getInstance().getTime());
            logFile.println();
        }
    }
    
    // this was an experiment. May revisit later.
    public static void logException(Exception ex) throws IOException  {

        String filename = "src/Log/exceptionLogger.txt";
        FileWriter fw = new FileWriter(filename, true);

        // Create and open file
        try (PrintWriter logFile = new PrintWriter(fw)) {
            logFile.println("Exception: " + ex.getMessage());
        }
    }

}
