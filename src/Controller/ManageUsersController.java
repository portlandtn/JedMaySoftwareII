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
import Utilities.SQL_Deleter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Jedidiah May
 */
public class ManageUsersController implements Initializable {
    
    UserDAO userDAO = new UserDAO();

    @FXML
    private RadioButton allRadioButton;

    @FXML
    private ToggleGroup AppointmentTypeToggle;

    @FXML
    private RadioButton activeRadioButton;

    @FXML
    private RadioButton inactiveRadioButton;

    @FXML
    private TableView<User> manageUsersTableView;

    @FXML
    private TableColumn<User, Integer> userIdColumnTableView;

    @FXML
    private TableColumn<User, String> usernameColumnTableView;

    @FXML
    private TableColumn<User, String> passwordColumnTableView;

    @FXML
    private TableColumn<User, Boolean> activeColumnTableView;

    @FXML
    void onActionDeleteUser(ActionEvent event) throws SQLException, ClassNotFoundException {
        DatabaseConnector.createConnection();
        userDAO.delete(manageUsersTableView.getSelectionModel().getSelectedItem());
        refreshData();
        DatabaseConnector.closeConnection();
    }

    @FXML
    void onActionDisplayDashboard(ActionEvent event) throws IOException {
        displayScreen("/View/Dashboard.fxml", event);
    }

    @FXML
    void onActionEditUser(ActionEvent event) {
        
        CreateEditUserController.isEditing = true;
        
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/CreateEditUser.fxml"));
            loader.load();
            
            CreateEditUserController.previousPath = "/View/ManageUsers.fxml";
            
            CreateEditUserController userController = loader.getController();
            userController.sendUserDetails(manageUsersTableView.getSelectionModel().getSelectedItem());
            
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
            
            displayScreen("/View/CreateEditUser.fxml", event);
            
            
        } catch (IOException | NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

        
    }

    @FXML
    void onActionCreateUser(ActionEvent event) throws IOException {
        CreateEditUserController.isEditing = false;
        displayScreen("/View/CreateEditUser.fxml", event);
    }

    @FXML
    void onActionSelectActive(ActionEvent event) {

    }

    @FXML
    void onActionSelectAll(ActionEvent event) {

    }

    @FXML
    void onActionSelectInactive(ActionEvent event) {

    }

    private void displayScreen(String path, ActionEvent event) throws IOException {

        Stage stage;
        Parent scene;

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    private void refreshData() {

        try {
            //Setup the user table with data from the database.
            DatabaseConnector.createConnection();
            
            ResultSet result = userDAO.queryTable();
            ObservableList<User> allUsers = User.getUserData(result);
            
            DatabaseConnector.closeConnection();

            manageUsersTableView.setItems(allUsers);
            userIdColumnTableView.setCellValueFactory(new PropertyValueFactory<>("userId"));
            usernameColumnTableView.setCellValueFactory(new PropertyValueFactory<>("userName"));
            passwordColumnTableView.setCellValueFactory(new PropertyValueFactory<>("password"));
            activeColumnTableView.setCellValueFactory(new PropertyValueFactory<>("active"));

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshData();

    }
}
