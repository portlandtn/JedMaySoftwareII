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
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Jedidiah May
 */
public class ManageUsersController implements Initializable {

    DatabaseConnector dc = new DatabaseConnector();
    Connection conn;
    UserDAO userDAO;

    public ManageUsersController() {
        try {
            conn = dc.createConnection();
            this.userDAO = new UserDAO(conn);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private RadioButton allRadioButton;

    @FXML
    private RadioButton activeRadioButton;

    @FXML
    private RadioButton inactiveRadioButton;

    @FXML
    private TextField searchTextField;

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
    void onActionDeleteUser(ActionEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this user?");

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (!ButtonType.CANCEL.equals(result)) {
                userDAO.remove(manageUsersTableView.getSelectionModel().getSelectedItem().getUserId());
                refreshData();
        }

    }

    @FXML
    void onActionDisplayDashboard(ActionEvent event) throws IOException, SQLException {
        displayScreen("/View/Dashboard.fxml", event);
    }

    @FXML
    void onActionSearch(ActionEvent event) {
        ObservableList<User> userSearchResultsList = FXCollections.observableArrayList();

        String searchText = searchTextField.getText();

        if (searchText.isEmpty()) {
            manageUsersTableView.setItems(userDAO.query());
            return;
        }

        userSearchResultsList.clear();

        if (Validator.isSearchStringNumber(searchTextField.getText())) {
            userSearchResultsList = userDAO.lookupUser(Integer.parseInt(searchText));

        } else {
            userSearchResultsList = userDAO.lookupUser(searchText);
        }

        if (userSearchResultsList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "A user was not found.");
            alert.showAndWait();
        } else {
            manageUsersTableView.setItems(userSearchResultsList);
        }
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

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (NullPointerException ex) {
            Alert alert = new Alert(AlertType.ERROR, "You must select a user to edit.");
            alert.showAndWait();
        }

    }

    @FXML
    void onActionCreateUser(ActionEvent event) throws IOException, SQLException {
        CreateEditUserController.isEditing = false;
        CreateEditUserController.previousPath = "/View/ManageUsers.fxml";
        displayScreen("/View/CreateEditUser.fxml", event);
    }

    @FXML
    void onActionSelectActive(ActionEvent event) {
        searchTextField.setText("");
        refreshData();
    }

    @FXML
    void onActionSelectAll(ActionEvent event) {
        searchTextField.setText("");
        refreshData();
    }

    @FXML
    void onActionSelectInactive(ActionEvent event) {
        searchTextField.setText("");
        refreshData();
    }


    private void refreshData() {
        ObservableList<User> allUsers;

        if (allRadioButton.isSelected()) {
            allUsers = userDAO.query();
        } else if (activeRadioButton.isSelected()) {
            allUsers = userDAO.queryActiveInactiveUsers(true);
        } else {
            allUsers = userDAO.queryActiveInactiveUsers(false);
        }

        //Setup the user table with data from the database.
        manageUsersTableView.setItems(allUsers);
        userIdColumnTableView.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernameColumnTableView.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordColumnTableView.setCellValueFactory(new PropertyValueFactory<>("password"));
        activeColumnTableView.setCellValueFactory(new PropertyValueFactory<>("active"));

    }

    private void displayScreen(String path, ActionEvent event) throws IOException, SQLException {

        Stage stage;
        Parent scene;

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        conn.close();
        stage.show();
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshData();

    }
}
