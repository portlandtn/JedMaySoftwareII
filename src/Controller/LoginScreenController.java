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
import Utilities.SQL_Queries;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class LoginScreenController implements Initializable {
    
    UserDAO userDAO = new UserDAO();

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label noUserFoundLabel;

    @FXML
    void onActionShowUserDetails(ActionEvent event) throws IOException {
        CreateEditUserController.previousPath = "/View/LoginScreen.fxml";
        CreateEditUserController.isEditing = false;
        displayScreen("/View/CreateEditUser.fxml", event);
    }

    @FXML
    void onActionShowDashboard(ActionEvent event) throws IOException {

        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();

        try {
            DatabaseConnector.createConnection();

            //verifies both the username and password are in the table and allows the user to log in if they are present.
            
            ResultSet result = userDAO.queryTableWithUsernameAndPassword(userName, password);

            //if no rows are returned, the deny access, show that the user is not found.
            if (!result.next()) {
                noUserFoundLabel.setVisible(true);
                DataProvider.setIsLoggedIn(false);
                DatabaseConnector.closeConnection();
            } //if the username and password matches, and the result if found, but they are not active, then the admin must make them active.
            //WILL NEED A NEW SCREEN TO MANAGE USERS, ACCESSIBLE BY ADMIN ONLY.
            else {
                if (result.getBoolean("active") == false) {
                    Alert alert = new Alert(AlertType.ERROR, "While this user does exist, their account has been made inactive. "
                            + "Please have the administrator update this account to use it for logging in.");
                    alert.showAndWait();
                    return;
                }

                DataProvider.setIsLoggedIn(true);
                noUserFoundLabel.setVisible(false);
                DatabaseConnector.closeConnection();
                DataProvider.setCurrentUser(userNameTextField.getText());
                displayScreen("/View/Dashboard.fxml", event);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void displayScreen(String path, ActionEvent event) throws IOException {

        Stage stage;
        Parent scene;

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
