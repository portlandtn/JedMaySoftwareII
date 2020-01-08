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
import Utilities.Navigator;
import Utilities.Validator;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
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
    
    //Constructor
    public LoginScreenController() {
        try {
            conn = dc.createConnection();
            this.userDAO = new UserDAO(conn);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
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

    // <editor-fold desc="Standard FXML Methods">
    // Login button is clicked.
    @FXML
    void onActionShowDashboard(ActionEvent event) throws SQLException, IOException {

        try {
            String[] userNameAndPassword = {userNameTextField.getText(), passwordTextField.getText()};
            //Perform the checks to see if the user can Login
            if (!canUserLogIn(userNameAndPassword)) {
                // If the user didn't pass the checks, the message string is customized to explain why. If the userName is null, it falls through to the catch 
                // and uses that message string there.
                String message = null;
                if (!Validator.textIsEntered(userNameAndPassword)) {
                    message = this.usernameAndPasswordCannotBeEmpty;
                } else if (!doUserNameAndPasswordExistInDatabase()) {
                    message = this.usernameAndPasswordDoNotMatch;
                } else if (!isUserActive()) {
                    message = this.usernameIsInactive;
                }
                DataProvider.setIsLoggedIn(false);
                Alert alert = new Alert(AlertType.ERROR, message);
                alert.showAndWait();
            } 
            // Otherwise, all checks are cleared, so the user can login and go to the dashboard.
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
    // </editor-fold>

    private boolean canUserLogIn(String[] userNameAndPassword) {
        // If all three checks are good, return true. If all three are not good, return false.
        // Used the validator for the first check since a DAO wouldn't be required.
        return Validator.textIsEntered(userNameAndPassword) && doUserNameAndPasswordExistInDatabase() && isUserActive();
    }
    
    // Check #2
    private boolean doUserNameAndPasswordExistInDatabase() {
        return userDAO.isUserNameandPasswordValid(userNameTextField.getText(), passwordTextField.getText());
    }
    
    // Check #3
    private boolean isUserActive() {
        return userDAO.isUserActive(userNameTextField.getText());
    }

    // Sets labels based on the regional language setting selected.
    private void setLabels(ResourceBundle rb) {
        titleLabel.setText(rb.getString("titleLabel"));
        logInLabel.setText(rb.getString("logInLabel"));
        loginButton.setText(rb.getString("loginButton"));
        userNameTextField.setPromptText(rb.getString("userNameTextFieldPromptText"));
        passwordTextField.setPromptText(rb.getString("passwordTextFieldPromptText"));
    }

    // Sets message text based on the regional language selected
    private void setMessageText(ResourceBundle rb) {
        this.usernameNotFound = rb.getString("usernameNotFound");
        this.usernameAndPasswordDoNotMatch = rb.getString("usernameAndPasswordDoNotMatch");
        this.usernameIsInactive = rb.getString("usernameIsInactive");
        this.usernameAndPasswordCannotBeEmpty = rb.getString("usernameAndPasswordCannotBeEmpty");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();
        // Even though a resource bundle is passed in, this overrides it on initialize (needed when the user logs out).
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n/Nat", locale);
        setLabels(resourceBundle);
        setMessageText(resourceBundle);
    }

}
