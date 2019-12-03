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
import DAO.CustomerDAO;
import Model.Appointment;
import Utilities.DatabaseConnector;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
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
public class AppointmentDetailController implements Initializable {

    DatabaseConnector dc = new DatabaseConnector();
    AppointmentDAO appointmentDAO;
    CustomerDAO customerDAO;
    static String previousPath;
    static Boolean isEditing;

    private int appointmentId, customerId, userId;
    private String title, customerName, userName, location, type, description, contact, url, createdBy, lastUpdateBy;
    private Date appointmentDate, start, end, createDate, lastUpdate;

    private Appointment appointmentToUpdate = new Appointment();

    void sendAppointmentDetails(Appointment appt) {

        customerNameComboBox.setValue(appt.getCustomerName());
        assignedToChoiceBox.setValue(appt.getUserName());
        titleTextField.setText(appt.getTitle());
        descriptionTextField.setText(appt.getDescription());
        locationChoicebox.setValue(appt.getLocation());
        contactTextField.setText(appt.getContact());
        urlTextField.setText(appt.getUrl());
        typeChoiceBox.setValue(appt.getType());
        dateDatePicker.setValue(LocalDate.MAX); //FIX LATER
        startTimeCholceBox.setValue("8:00"); //FIX LATER
        endTimeChoiceBox.setValue("8:30"); //FIX LATER

        this.appointmentToUpdate = appt;
    }

    @FXML
    private ComboBox<String> customerNameComboBox;

    @FXML
    private ChoiceBox<String> assignedToChoiceBox;

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

    public AppointmentDetailController() {
        try {
            this.appointmentDAO = new AppointmentDAO(dc.createConnection());
            this.customerDAO = new CustomerDAO(dc.createConnection());
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        displayScreen(previousPath, event);
    }

    @FXML
    void onActionDatePicked(ActionEvent event) {

    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        
        if(!customerDAO.doesCustomerExist(customerNameComboBox.getValue())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This customer does not exist. A new customer will have to be created. Would you like to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() != ButtonType.OK){
                return;
            } else {
                displayScreen("/View/CreateEditCustomer.fxml", event);
            }
        }

        if (!canDataBeSaved()) {
            return;
        }

        if (isEditing) {
            updateAppointment(this.appointmentToUpdate);
        } else {
            saveNewAppointment();
        }
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

    private void updateAppointment(Appointment existingAppt) {
        
        setAppointmenetTableVariables();
        
        try (Connection conn = dc.createConnection()) {
            Appointment appt = new Appointment();
            appt.setAppointmentId(existingAppt.getAppointmentId());
            appt.setCustomerName(this.customerName);
            appt.setUserName(this.userName);
            appt.setTitle(this.title);
            appt.setDescription(this.description);
            appt.setLocation(this.location);
            appt.setType(this.type);
            appt.setContact(this.contact);
            appt.setUrl(this.url);
            appt.setAppointmentDate(this.appointmentDate);
            appt.setStart(this.start);
            appt.setEnd(this.end);
            
            appointmentDAO.update(appt);
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void saveNewAppointment() {
        
        setAppointmenetTableVariables();
        
        try (Connection conn = dc.createConnection()) {
            Appointment appt = new Appointment();
            appt.setCustomerName(this.customerName);
            appt.setUserName(this.userName);
            appt.setTitle(this.title);
            appt.setDescription(this.description);
            appt.setLocation(this.location);
            appt.setType(this.type);
            appt.setContact(this.contact);
            appt.setUrl(this.url);
            appt.setAppointmentDate(this.appointmentDate);
            appt.setStart(this.start);
            appt.setEnd(this.end);
            
            appointmentDAO.insert(appt);
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void setAppointmenetTableVariables() {
        this.customerName = customerNameComboBox.getValue();
        this.userName = assignedToChoiceBox.getSelectionModel().getSelectedItem();
        this.title = titleTextField.getText();
        this.description = descriptionTextField.getText();
        this.location = locationChoicebox.getSelectionModel().getSelectedItem();
        this.contact = contactTextField.getText();
        this.url = urlTextField.getText();
        this.type = typeChoiceBox.getSelectionModel().getSelectedItem();
        this.appointmentDate = new Date(System.currentTimeMillis());
        this.start = new Date(System.currentTimeMillis());
        this.end = new Date(System.currentTimeMillis());
    }

    private Boolean canDataBeSaved() {
        String[] textFields = new String[]{
            customerNameComboBox.getValue(),
            titleTextField.getText()};
        return Utilities.Validator.isTextEntered(textFields);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
