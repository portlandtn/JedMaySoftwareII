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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Jedidiah May
 */
public class DashboardController implements Initializable {

    @FXML
    private Button manageUsersButton;

    @FXML
    void onActionLogOut(ActionEvent event) throws IOException {
        DataProvider.setIsLoggedIn(false);
        DataProvider.setCurrentUser(null);
        displayScreen("/View/LoginScreen.fxml", event);
    }

    @FXML
    void onActionShowCalendar(ActionEvent event) throws IOException {
        displayScreen("/View/AppointmentsCalendar.fxml", event);
    }

    @FXML
    void onActionShowCustomers(ActionEvent event) throws IOException {
        displayScreen("/View/ManageCustomers.fxml", event);
    }

    @FXML
    void onActionShowReportsDashboard(ActionEvent event) throws IOException {
        displayScreen("/View/ReportsDashboard.fxml", event);
    }

    @FXML
    void onActionShowManageUsersScreen(ActionEvent event) throws IOException {
        displayScreen("/View/ManageUsers.fxml", event);
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
        
        //If you're not the admin, the Manage Users button won't be enabled.
        manageUsersButton.setDisable(!"admin".equals(DataProvider.getCurrentUser()));

    }

}
