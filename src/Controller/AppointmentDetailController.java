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
import DAO.UserDAO;
import Model.Appointment;
import Utilities.DataProvider;
import Utilities.DatabaseConnector;
import Utilities.Navigator;
import Utilities.Validator;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 *
 * @author Jedidiah May
 */
public class AppointmentDetailController implements Initializable {

    DatabaseConnector dc = new DatabaseConnector();
    Connection conn;
    AppointmentDAO appointmentDAO;
    CustomerDAO customerDAO;
    UserDAO userDAO;
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
        locationChoiceBox.setValue(appt.getLocation());
        contactTextField.setText(appt.getContact());
        urlTextField.setText(appt.getUrl());
        typeChoiceBox.setValue(appt.getType());
        dateDatePicker.setValue(LocalDate.MAX); //FIX LATER
        startTimeChoiceBox.setValue("8:00"); //FIX LATER
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
    private ChoiceBox<String> locationChoiceBox;

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
    private ChoiceBox<String> startTimeChoiceBox;

    @FXML
    private ChoiceBox<String> endTimeChoiceBox;

    public AppointmentDetailController() {
        try {
            this.conn = dc.createConnection();
            this.appointmentDAO = new AppointmentDAO(conn);
            this.customerDAO = new CustomerDAO(conn);
            this.userDAO = new UserDAO(conn);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException, SQLException {
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(previousPath)));
    }

    @FXML
    void onActionDatePicked(ActionEvent event) {

    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException, ParseException {

        if (!customerDAO.doesCustomerExist(customerNameComboBox.getValue())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This customer does not exist. A new customer will have to be created. Would you like to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                return;
            } else {
                CreateEditCustomerController.previousPath = DataProvider.pathOfFXML.APPOINTMENT_DETAIL.getPath();
                Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(DataProvider.pathOfFXML.CREATE_EDIT_CUSTOMER.getPath())));
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
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(previousPath)));
    }

    private void updateAppointment(Appointment existingAppt) {

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

    }

    private void saveNewAppointment() throws ParseException {

        Appointment appt = new Appointment();
        appt.setCustomerName(customerNameComboBox.getValue());
        appt.setUserName(assignedToChoiceBox.getValue());
        appt.setTitle(titleTextField.getText());
        appt.setDescription(descriptionTextField.getText());
        appt.setLocation(locationChoiceBox.getValue());
        appt.setType(typeChoiceBox.getValue());
        appt.setContact(contactTextField.getText());
        appt.setUrl(urlTextField.getText());
        
        java.util.Date apptDate = java.sql.Date.valueOf(dateDatePicker.getValue());
        appt.setAppointmentDate(apptDate);
  
        Date startTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(startTimeChoiceBox.getValue());
        appt.setStart(startTime);
        
        Date endTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(endTimeChoiceBox.getValue());
        appt.setEnd(endTime);

        appointmentDAO.insert(appt);

    }

    private Boolean canDataBeSaved() {
        String[] textFields = new String[]{
            customerNameComboBox.getValue(),
            titleTextField.getText(),
            dateDatePicker.getValue().toString(),
            startTimeChoiceBox.getValue(),
            endTimeChoiceBox.getValue()};
        
        if (!Validator.isTextEntered(textFields)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "At a minimum, you must have a Customer Name, Title, Date, Start Time, and End Time entered to save.");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerNameComboBox.setItems(customerDAO.queryAllCustomers());
        assignedToChoiceBox.setItems(userDAO.queryAllUsers());
        locationChoiceBox.setItems(DataProvider.LOCATIONS);
        typeChoiceBox.setItems(DataProvider.APPOINTMENT_TYPES);
        startTimeChoiceBox.setItems(DataProvider.operatingHours);
        endTimeChoiceBox.setItems(DataProvider.operatingHours);
    }
}
