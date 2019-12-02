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
import Utilities.DatabaseConnector;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
public class AppointmentDetailController implements Initializable {
    
    AppointmentDAO appointmentDAO = new AppointmentDAO();

    @FXML
    private TextField appointmentIDTextField;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private ChoiceBox<String>  assignedToChoiceBox;

    @FXML
    private TextField titleTextField;

    @FXML
    private ChoiceBox<String> locationChoicebox;

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @FXML
    private TextField urlTextField;

    @FXML
    private TextField contactTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private DatePicker dateDatePicker;

    @FXML
    private ChoiceBox<String> startTimeCholceBox;

    @FXML
    private ChoiceBox<String> endTimeChoiceBox;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        displayScreen("/View/AppointmentsCalendar.fxml", event);
    }

    @FXML
    void onActionDatePicked(ActionEvent event) {

    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        displayScreen("/View/AppointmentsCalendar.fxml", event);
    }
    
    private void displayScreen(String path, ActionEvent event) throws IOException {

        Stage stage;
        Parent scene;

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    private void refreshData(){
        
        try {
            
            DatabaseConnector.createConnection();
            ResultSet results  = appointmentDAO.queryTableWithJoins();
            
            ObservableList<Appointment> allAppointments = Appointment.getAllAppointments(results);
            DatabaseConnector.closeConnection();
            
//            appointmentIDTextField.setText(value);
//            customerNameTextField.setText(value);
//            assignedToChoiceBox.setValue(value);
//            titleTextField.setText(value);
//            locationChoicebox.setValue(value);
//            typeChoiceBox.setValue(value);
//            urlTextField.setText(value);
//            contactTextField.setText(values);
//            descriptionTextField.setText(values);
//            dateDatePicker.setValue(LocalDate.MAX);
//            startTimeCholceBox.setValue(value);
//            endTimeChoiceBox.setValue(values);


        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refreshData();
    }

}
