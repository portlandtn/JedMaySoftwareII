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
import Utilities.Validator;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 *
 * @author Jedidiah May
 */
public class CreateEditUserController implements Initializable {

//    UserDAO userDAO = new UserDAO();

    public static Boolean isEditing;
    public static String previousPath;

    //Variables to be used for user object
    private String userName;
    private String password;
    private Boolean active;
    private Date createDate;
    private String createdBy;
    private Date lastModified;
    private String lastModifiedBy;

    private User userToUpdate;

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private CheckBox activeCheckBox;

    @FXML
    void onActionActive_Inactive(ActionEvent event) {

    }

    @FXML
    void onActionSaveUser(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        if (isEditing) {
            updateExistingUser(this.userToUpdate);
        } else {
            saveNewUser();
        }
        displayScreen(previousPath, event);

    }

    @FXML
    void onActionGoBack(ActionEvent event) throws IOException {
        displayScreen(previousPath, event);
    }

    private void displayScreen(String path, ActionEvent event) throws IOException {

        Stage stage;
        Parent scene;

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void sendUserDetails(User user) {

        userNameTextField.setText(user.getUserName());
        passwordTextField.setText(user.getPassword());
        confirmPasswordTextField.setText(user.getPassword());
        activeCheckBox.setSelected(user.getActive());
        this.userToUpdate = user;
    }

    private void saveNewUser() throws ClassNotFoundException, SQLException, IOException {

        if (!canDataBeSaved()) {
            return;
        }

        //if all is verified good, declare variables to construct a new User and insert into the table.
//        setUserTableVariables();
//
//        User user = new User(0, this.userName, this.password, this.active, this.createDate, this.createdBy, this.lastModified, this.lastModifiedBy);
//        
//        DatabaseConnector.createConnection();
//        userDAO.create(user);
//        DatabaseConnector.closeConnection();

    }

    private void setUserTableVariables() {
        this.userName = userNameTextField.getText();
        this.password = passwordTextField.getText();
        this.active = activeCheckBox.isSelected();
        this.createDate = new Date(System.currentTimeMillis());
        this.createdBy = userNameTextField.getText();
        this.lastModified = new Date(System.currentTimeMillis());
        this.lastModifiedBy = userNameTextField.getText();
    }

    private void updateExistingUser(User existingUser) throws SQLException, ClassNotFoundException {

//        //if there is some kind of error, abort.
//        if (!canDataBeSaved()) {
//            return;
//        }
//
//        DatabaseConnector.createConnection();
//
//        //if all is verified good, declare variables to construct a new User and update the table.
//        setUserTableVariables();
//
//        User user = new User(existingUser.getUserId(), this.userName, this.password, this.active, this.createDate, this.createdBy, this.lastModified, this.lastModifiedBy);
//
//        userDAO.update(user);
//        DatabaseConnector.closeConnection();
    }

    private Boolean canDataBeSaved() {

        //Setup the string array that holds the text fields to verify that are not empty.
        String[] textFields = new String[]{
            userNameTextField.getText(),
            passwordTextField.getText(),
            confirmPasswordTextField.getText()};
        if (!Validator.isTextEntered(textFields)) {
            Alert alert = new Alert(AlertType.ERROR, "All text fields must have data");
            alert.showAndWait();
            return false;
        }

        //verify that the two passwords entered are not empty
        if (passwordTextField.getText().isEmpty() | confirmPasswordTextField.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "Password fields cannot be blank.");
            alert.showAndWait();
            return false;
        }
        //verifty that the two passwords match
        if (!Validator.doStringsMatch(passwordTextField.getText(), confirmPasswordTextField.getText())) {
            Alert alert = new Alert(AlertType.ERROR, "The passwords entered do not match. Please re-enter and try again.");
            alert.showAndWait();
            return false;
        }

        // if these three checks are good, input is valid and can be saved.
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
