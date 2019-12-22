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
import Utilities.LanguageConverter;
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
    private Label titleLabel;

    @FXML
    private Label logInLabel;

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button createUserButton;

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
    void onActionShowDashboard(ActionEvent event) throws SQLException, IOException {

        try {
            
            //Perform the checks to see if the user can Login
            if(!canUserLogIn()) {
                String message = getErrorMessageForLogin();
                DataProvider.setIsLoggedIn(false);
                Alert alert = new Alert(AlertType.ERROR, message);
                alert.showAndWait();
            }

            // All checks are cleared, so the user can login and go to the dashboard.
             else {
                DataProvider.setIsLoggedIn(true);
                DataProvider.setCurrentUser(userNameTextField.getText());
                Logger.logUserLogin();
                Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(DataProvider.pathOfFXML.DASHBOARD.getPath())));
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (NullPointerException ex) {
            System.out.println("Expected. User is null: " + ex.getMessage());
            DataProvider.setIsLoggedIn(false);
            String message = LanguageConverter.translateUserNameNotFound();
            Alert alert = new Alert(AlertType.ERROR, message);
            alert.showAndWait();
        }
    }

    private Boolean canUserLogIn() {
        
        // If username or password text fields are empty, display error message.
        if (userNameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
            return false;
        } 

        // If userName is not found, then alert that the user does not exist.
        else if (!userDAO.isUserNameandPasswordValid(userNameTextField.getText(), passwordTextField.getText())) {
            return false;

        // If user is inactive, warn that the user cannot log in without first being made active.
        } else if (!userDAO.isUserActive(userNameTextField.getText())) {
            return false;
        }
        else return true;
        
    }
    
    private String getErrorMessageForLogin() {

        // If username or password text fields are empty, display error message.
        if (userNameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
            return LanguageConverter.translateUserNameAndPasswordCannotBeEmpty();
        } // If userName is not found, then alert that the user does not exist.
        else if (!userDAO.isUserNameandPasswordValid(userNameTextField.getText(), passwordTextField.getText())) {
            return LanguageConverter.translateUserNameAndPasswordAreInvalid();

            // If user is inactive, warn that the user cannot log in without first being made active.
        } else if (!userDAO.isUserActive(userNameTextField.getText())) {
            return LanguageConverter.translateUserNameInactive();
        } else {
            return null;
        }

    }
    
    

    private void translateLabelsButtons() {
        switch (DataProvider.getLanguage()) {
            case "English":
                titleLabel.setText(LanguageConverter.schedulerLabel.ENGLISH.getText());
                logInLabel.setText(LanguageConverter.pleaseLogInToContinueLabel.ENGLISH.getText());
                loginButton.setText(LanguageConverter.loginButton.ENGLISH.getText());
                createUserButton.setText(LanguageConverter.createUserButton.ENGLISH.getText());
                userNameTextField.setPromptText(LanguageConverter.usernameHintText.ENGLISH.getText());
                passwordTextField.setPromptText(LanguageConverter.passwordHintText.ENGLISH.getText());
                break;
            case "French":
                titleLabel.setText(LanguageConverter.schedulerLabel.FRENCH.getText());
                logInLabel.setText(LanguageConverter.pleaseLogInToContinueLabel.FRENCH.getText());
                loginButton.setText(LanguageConverter.loginButton.FRENCH.getText());
                createUserButton.setText(LanguageConverter.createUserButton.FRENCH.getText());
                userNameTextField.setPromptText(LanguageConverter.usernameHintText.FRENCH.getText());
                passwordTextField.setPromptText(LanguageConverter.passwordHintText.FRENCH.getText());
                break;
            case "Spanish":
                titleLabel.setText(LanguageConverter.schedulerLabel.SPANISH.getText());
                logInLabel.setText(LanguageConverter.pleaseLogInToContinueLabel.SPANISH.getText());
                loginButton.setText(LanguageConverter.loginButton.SPANISH.getText());
                createUserButton.setText(LanguageConverter.createUserButton.SPANISH.getText());
                userNameTextField.setPromptText(LanguageConverter.usernameHintText.SPANISH.getText());
                passwordTextField.setPromptText(LanguageConverter.passwordHintText.SPANISH.getText());
                break;
            case "German":
                titleLabel.setText(LanguageConverter.schedulerLabel.GERMAN.getText());
                logInLabel.setText(LanguageConverter.pleaseLogInToContinueLabel.GERMAN.getText());
                loginButton.setText(LanguageConverter.loginButton.GERMAN.getText());
                createUserButton.setText(LanguageConverter.createUserButton.GERMAN.getText());
                userNameTextField.setPromptText(LanguageConverter.usernameHintText.GERMAN.getText());
                passwordTextField.setPromptText(LanguageConverter.passwordHintText.GERMAN.getText());
                break;
            default:
                titleLabel.setText(LanguageConverter.schedulerLabel.ENGLISH.getText());
                logInLabel.setText(LanguageConverter.pleaseLogInToContinueLabel.ENGLISH.getText());
                loginButton.setText(LanguageConverter.loginButton.ENGLISH.getText());
                createUserButton.setText(LanguageConverter.createUserButton.ENGLISH.getText());
                userNameTextField.setPromptText(LanguageConverter.usernameHintText.ENGLISH.getText());
                passwordTextField.setPromptText(LanguageConverter.passwordHintText.ENGLISH.getText());
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DateTimeConverter.currentTimeZoneId = TimeZone.getDefault().getID();
        DataProvider.setStartingHours();
        DataProvider.setLanguage("English");
        translateLabelsButtons();
    }

}
