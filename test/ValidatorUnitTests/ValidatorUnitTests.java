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
package ValidatorUnitTests;
import Utilities.Validator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jedidiah May
 */
public class ValidatorUnitTests {
    
    @Test
    public void isTextEnteredTestShouldReturnTrue(){
        
        String[] testStringArray = new String[] {"yes", "no", "maybe so"};
        
        boolean expectedResult = true;
        boolean actualResult = Validator.isTextEntered(testStringArray);
        
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
    public void isTextEnteredTestShouldReturnFalse(){
        
        String[] testStringArray = new String[] {"yes",""};
        
        boolean expectedResult = false;
        boolean actualResult = Validator.isTextEntered(testStringArray);
        
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
    public void doStringsMatchShouldReturnTrue() {

        String string1 = "testing";
        String string2 = "testing";

        boolean expectedResult = true;
        boolean actualResult = Validator.doStringsMatch(string1,string2);

        assertEquals(expectedResult, actualResult);
    }
    
    @Test
    public void doStringsMatchShouldReturnFalse() {

        String string1 = "testing";
        String string2 = "test";

        boolean expectedResult = false;
        boolean actualResult = Validator.doStringsMatch(string1, string2);

        assertEquals(expectedResult, actualResult);
    }
}
