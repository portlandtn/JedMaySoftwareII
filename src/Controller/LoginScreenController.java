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
import Utilities.DateTimeConverter;
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
    
    // Messages for alerts
    String usernameNotFound, usernameAndPasswordDoNotMatch, usernameIsInactive, usernameAndPasswordCannotBeEmpty;

    // <editor-fold defaultstate="collapsed" desc="FXML objects">
    @FXML
    private Label titleLabel;

    @FXML
    private Label logInLabel;

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

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

    // Login button is clicked.
    @FXML
    void onActionShowDashboard(ActionEvent event) throws SQLException, IOException {

        try {

            //Perform the checks to see if the user can Login
            if (!canUserLogIn()) {
            String message = null;
            if (!doUserNameAndPasswordFieldsHaveText())
                message = this.usernameAndPasswordCannotBeEmpty;
            
            else if (!doUserNameAndPasswordExistInDatabase())
                message = this.usernameAndPasswordDoNotMatch;
            
            else if (!isUserActive())
                message = this.usernameIsInactive;
                DataProvider.setIsLoggedIn(false);
                Alert alert = new Alert(AlertType.ERROR, message);
                alert.showAndWait();
            } 

            // All checks are cleared, so the user can login and go to the dashboard.
            else {
                DataProvider.setIsLoggedIn(true);
                DataProvider.setCurrentUser(userNameTextField.getText());
                Logger.logUserLogin();
                Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(Navigator.pathOfFXML.DASHBOARD.getPath())));
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        } catch (NullPointerException ex) {
            System.out.println("Expected. User is null: " + ex.getMessage());
            DataProvider.setIsLoggedIn(false);
            String message = this.usernameNotFound;
            Alert alert = new Alert(AlertType.ERROR, message);
            alert.showAndWait();
        }
    }

    private Boolean canUserLogIn() {

        // Are all three checks good? If so, return true. If all three are not good, return false.
        return doUserNameAndPasswordFieldsHaveText() && doUserNameAndPasswordExistInDatabase() && isUserActive();

    }

    private Boolean doUserNameAndPasswordFieldsHaveText() {
        return !(userNameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty());
    }

    private Boolean doUserNameAndPasswordExistInDatabase() {
        return userDAO.isUserNameandPasswordValid(userNameTextField.getText(), passwordTextField.getText());
    }

    private Boolean isUserActive() {
        return userDAO.isUserActive(userNameTextField.getText());
    }
    
    private void setLabels(ResourceBundle rb) {
        titleLabel.setText(rb.getString("titleLabel"));
        logInLabel.setText(rb.getString("logInLabel"));
        loginButton.setText(rb.getString("loginButton"));
        userNameTextField.setPromptText(rb.getString("userNameTextFieldPromptText"));
        passwordTextField.setPromptText(rb.getString("passwordTextFieldPromptText"));
    }
    
    private void setMessageText(ResourceBundle rb) {
        this.usernameNotFound = rb.getString("usernameNotFound");
        this.usernameAndPasswordDoNotMatch = rb.getString("usernameAndPasswordDoNotMatch");
        this.usernameIsInactive = rb.getString("usernameIsInactive");
        this.usernameAndPasswordCannotBeEmpty = rb.getString("usernameAndPasswordCannotBeEmpty");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DateTimeConverter.currentTimeZoneId = TimeZone.getDefault().getID();
        DataProvider.setStartingHours();
        setLabels(rb);
        setMessageText(rb);
    }

}
