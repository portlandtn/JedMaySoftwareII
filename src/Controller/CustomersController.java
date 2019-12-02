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

import Model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 *
 * @author Jedidiah May
 */
public class CustomersController implements Initializable {

    @FXML
    private TableView<Appointment> calendarTableView;

    @FXML
    private TableColumn<Appointment, Integer> idColumnTableView;

    @FXML
    private TableColumn<Appointment, String> nameColumnTableView;

    @FXML
    private TableColumn<Appointment, String> addressColumnTableView;

    @FXML
    private TableColumn<Appointment, String> address2ColumnTableView;

    @FXML
    private TableColumn<Appointment, String> cityColumnTableView;

    @FXML
    private TableColumn<Appointment, String> zipCodeColumnTableView;

    @FXML
    private TableColumn<Appointment, String> countryColumnTableView;

    @FXML
    private TableColumn<Appointment, String> phoneColumnTableView;
    
    @FXML
    void onActionShowDashboard(ActionEvent event) throws IOException {
        displayScreen("/View/Dashboard.fxml", event);
    }

    @FXML
    void onActionAddNewCustomer(ActionEvent event) throws IOException {
        displayScreen("/View/EditCustomers.fxml", event);
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

    }

    @FXML
    void onActionEditCustomer(ActionEvent event) throws IOException {
        displayScreen("/View/EditCustomers.fxml", event);
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
