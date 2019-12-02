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

import DAO.AppointmentDAO;
import Model.Appointment;
import Model.User;
import Utilities.DatabaseConnector;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
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
public class AppointmentsCalendarController implements Initializable {
    
    AppointmentDAO appointmentDAO = new AppointmentDAO();

    @FXML
    private RadioButton allRadioButton;

    @FXML
    private ToggleGroup CalendarViewToggle;

    @FXML
    private RadioButton monthRadioButton;

    @FXML
    private RadioButton weekRadioButton;

    @FXML
    private TableView<Appointment> calendarAppointmentTableView;

    @FXML
    private TableColumn<Appointment, String> customerColumnTableView;

    @FXML
    private TableColumn<Appointment, String> assignedToColumnTableView;

    @FXML
    private TableColumn<Appointment, String> locationColumnTableView;

    @FXML
    private TableColumn<Appointment, String> contactColumnTableView;

    @FXML
    private TableColumn<Appointment, Date> dateColumnTableView;

    @FXML
    private TableColumn<Appointment, Date> startColumnTableView;

    @FXML
    private TableColumn<Appointment, Date> endColumnTableView;

    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        displayScreen("/View/AppointmentsDetail.fxml", event);
    }

    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        displayScreen("/View/Dashboard.fxml", event);
    }

    @FXML
    void onActionDeleteAppointment(ActionEvent event) {

    }

    @FXML
    void onActionEditAppointment(ActionEvent event) throws IOException {
        displayScreen("/View/AppointmentsDetail.fxml", event);
    }

    @FXML
    void onActionDisplayAppointmentDetail(ActionEvent event) throws IOException {
        displayScreen("/View/AppointmentsDetail.fxml", event);
    }

    @FXML
    void onActionSelectAll(ActionEvent event) {

    }

    @FXML
    void onActionSelectMonth(ActionEvent event) {

    }

    @FXML
    void onActionSelectWeek(ActionEvent event) {

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
            ResultSet results = appointmentDAO.queryTableWithJoins();
            ObservableList<Appointment> allAppointments = Appointment.getAllAppointments(results);
            DatabaseConnector.closeConnection();

            calendarAppointmentTableView.setItems(allAppointments);
            customerColumnTableView.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            assignedToColumnTableView.setCellValueFactory(new PropertyValueFactory<>("assignedToUser"));
            locationColumnTableView.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactColumnTableView.setCellValueFactory(new PropertyValueFactory<>("contact"));
            dateColumnTableView.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
            startColumnTableView.setCellValueFactory(new PropertyValueFactory<>("start"));
            endColumnTableView.setCellValueFactory(new PropertyValueFactory<>("end"));

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshData();
    }

}
