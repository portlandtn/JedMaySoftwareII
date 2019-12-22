/* 
 * Copyright (C) 2019 Jedidiah May
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
package Controller;

import Utilities.DataProvider;
import Utilities.DatabaseConnector;
import DAO.UserDAO;
import Log.Logger;
import Utilities.Converter;
import Utilities.Navigator;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Jedidiah May
 */
public class LoginScreenController implements Initializable {

    DatabaseConnector dc = new DatabaseConnector();
    Connection conn;
    UserDAO userDAO;

    // <editor-fold defaultstate="collapsed" desc="FXML objects">
    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label noUserFoundLabel;
    // </editor-fold>

    //Constructor
    public LoginScreenController() {
        try {
            conn = dc.createConnection();
            this.userDAO = new UserDAO(conn);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // This is used when creating a new user. 
    @FXML
    void onActionShowUserDetails(ActionEvent event) throws IOException, SQLException {
        // Sets the previous path for navigation back here from the next screen (static field)
        CreateEditUserController.previousPath = DataProvider.pathOfFXML.LOGIN_SCREEN.getPath();
        // Static field that sets code flow based on whether or not form is for editing or creating a new user (flag)
        CreateEditUserController.isEditing = false;
        conn.close();
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(DataProvider.pathOfFXML.CREATE_EDIT_USER.getPath())));

    }

    // Login button is clicked.
    @FXML
    void onActionShowDashboard(ActionEvent event) throws SQLException {

        try {
            String userName = userDAO.getUserName(userNameTextField.getText());

            // If userName is not found, then alert that the user does not exist.
            if (userName.isEmpty()) {
                noUserFoundLabel.setVisible(true);
                DataProvider.setIsLoggedIn(false);
                Alert alert = new Alert(AlertType.ERROR, "The username does not exist.");
                alert.showAndWait();

                // If user exists, but the username and password do not match, alert the user.
            } else if (!userDAO.isUserNameandPasswordValid(userNameTextField.getText(), passwordTextField.getText())) {
                noUserFoundLabel.setVisible(true);
                DataProvider.setIsLoggedIn(false);
                Alert alert = new Alert(AlertType.ERROR, "The username and password does not match.");
                alert.showAndWait();

                // If user is inactive, warn that the user cannot log in without first being made active.
            } else if (!userDAO.isUserActive(userNameTextField.getText())) {
                Alert alert = new Alert(AlertType.ERROR, "While this user does exist, their account has been made inactive. "
                        + "Please have the administrator update this account to use it for logging in.");
                alert.showAndWait();
                
                // All checks are cleared, so the user can login and go to the dashboard.
            } else {
                DataProvider.setIsLoggedIn(true);
                noUserFoundLabel.setVisible(false);
                DataProvider.setCurrentUser(userNameTextField.getText());
                Logger.logUserLogin();
                Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(DataProvider.pathOfFXML.DASHBOARD.getPath())));

            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (NullPointerException ex) {
            noUserFoundLabel.setVisible(true);
            DataProvider.setIsLoggedIn(false);
            Alert alert = new Alert(AlertType.ERROR, "The username does not exist.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Converter.currentTimeZoneId = TimeZone.getDefault().getID();
        DataProvider.setStartingHours();
    }

}
