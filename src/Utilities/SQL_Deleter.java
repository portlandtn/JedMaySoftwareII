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

import Model.*;
//import static DAO.DatabaseConnector.databaseConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/*
* THIS WILL ALL LIKELY GET DELETED!!!!
*/
/**
 *
 * @author Jedidiah May
 */
public class SQL_Deleter {

//    public static void deleteFromTable(String tableName, String idColumnName, int id) throws SQLException {
//
//        String deleteStatement = "DELETE FROM " + tableName + " where " + idColumnName + " = ?";
//
//        try {
//            //Open Database Connection
//            DatabaseConnector.createConnection();
//
//            //Setup the prepared statement
//            PreparedStatement stmt = databaseConnection.prepareStatement(deleteStatement);
//
//            //Insert the parameters into the statement
//            stmt.setInt(1, id);
//
//            //Execute the query
//            stmt.executeUpdate();
//        } catch (SQLException | ClassNotFoundException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            //Regardless, close DB connection
//            databaseConnection.close();
//        }
//    }

}
