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

import Model.User;
import Utilities.DatabaseConnector;
import DAO.UserDAO;
import Utilities.Navigator;
import Utilities.Validator;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
public class CreateEditUserController implements Initializable {

    DatabaseConnector dc = new DatabaseConnector();
    Connection conn;
    UserDAO userDAO;

    public static boolean isEditing;
    public static String previousPath;

    private User userToUpdate;

    // <editor-fold defaultstate="collapsed" desc="FXML objects">
    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private CheckBox activeCheckBox;
    // </editor-fold>

    public CreateEditUserController() {
        try {
            conn = dc.createConnection();
            this.userDAO = new UserDAO(conn);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void onActionActive_Inactive(ActionEvent event) {

    }

    @FXML
    void onActionSaveUser(ActionEvent event) throws IOException, SQLException {

        if (!canDataBeSaved()) {
            return;
        }

        if (isEditing) {
            updateExistingUser(this.userToUpdate);
        } else {
            saveNewUser();
        }
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(previousPath)));

    }

    @FXML
    void onActionGoBack(ActionEvent event) throws IOException, SQLException {
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(previousPath)));
    }

    public void sendUserDetails(User user) {

        userNameTextField.setText(user.getUserName());
        passwordTextField.setText(user.getPassword());
        confirmPasswordTextField.setText(user.getPassword());
        activeCheckBox.setSelected(user.getActive());
        this.userToUpdate = user;
    }

    private void saveNewUser() {

        // If all is verified good, declare variables to construct a new User and insert into the table.
        //setVariablesFromScreen();
        User user = new User();
        user.setUserName(userNameTextField.getText());
        user.setPassword(passwordTextField.getText());
        user.setActive(activeCheckBox.isSelected());

        userDAO.insert(user);
    }

    private void updateExistingUser(User existingUser) {

        // If all is verified good, declare variables to construct a new User and update the table.
        //setVariablesFromScreen();
        User user = new User();
        user.setUserId(existingUser.getUserId());
        user.setUserName(userNameTextField.getText());
        user.setPassword(passwordTextField.getText());
        user.setActive(activeCheckBox.isSelected());

        userDAO.update(user);
    }

    // Performs 3 checks to see if data can be saved.
    private boolean canDataBeSaved() {

        // Setup the string array that holds the text fields to verify that are not empty.
        String[] textFields = new String[]{
            userNameTextField.getText(),
            passwordTextField.getText(),
            confirmPasswordTextField.getText()};   
        if (!Validator.textIsEntered(textFields)) {
            Alert alert = new Alert(AlertType.ERROR, "All text fields must have data");
            alert.showAndWait();
            return false;
        }

        // Verifty that the two passwords match
        else if (!Validator.stringDoMatch(passwordTextField.getText(), confirmPasswordTextField.getText())) {
            Alert alert = new Alert(AlertType.ERROR, "The passwords entered do not match. Please re-enter and try again.");
            alert.showAndWait();
            return false;
        }

        // If these three checks are good, input is valid and can be saved.
        else return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
