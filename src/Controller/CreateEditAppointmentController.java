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
import Utilities.DateTimeConverter;
import Utilities.I_Validator;
import Utilities.Navigator;
import Utilities.Validator;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
public class CreateEditAppointmentController implements Initializable {

    DatabaseConnector dc = new DatabaseConnector();
    Connection conn;
    AppointmentDAO appointmentDAO;
    CustomerDAO customerDAO;
    UserDAO userDAO;
    static String previousPath;
    static boolean isEditing;

    private Appointment appointmentToUpdate = new Appointment();

    public CreateEditAppointmentController() {
        try {
            this.conn = dc.createConnection();
            this.appointmentDAO = new AppointmentDAO(conn);
            this.customerDAO = new CustomerDAO(conn);
            this.userDAO = new UserDAO(conn);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="FXML Objects">
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
    private TextField startTimeTextField;

    @FXML
    private TextField endTimeTextField;
    // </editor-fold>

    // <editor-fold desc="Standard FXML Methods">
    
    @FXML
    void onActionCancel(ActionEvent event) throws IOException, SQLException {
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(previousPath)));
    }

    @FXML
    void onActionDatePicked(ActionEvent event) {

    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException, ParseException {

        // Massive validator method. Must pass all checks before data can be saved.
        if (!canDataBeSaved()) {
            return;
        }

        // Checks if the customer exists. If it does not, it warns the user and gives an option to create the user prior to saving.
        if (!customerDAO.doesCustomerExist(customerNameComboBox.getValue())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This customer does not exist. A new customer will have to be created. "
                    + "Would you like to continue creating this customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                return;
            } else {
                CreateEditCustomerController.previousPath = Navigator.pathOfFXML.CREATE_EDIT_APPOINTMENT.getPath();
                Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(Navigator.pathOfFXML.CREATE_EDIT_CUSTOMER.getPath())));
            }
        }

        if (isEditing) {
            updateAppointment(this.appointmentToUpdate);
        } else {
            saveNewAppointment();
        }
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(previousPath)));
    }
    // </editor-fold>
    void sendAppointmentDetails(Appointment appt) {

        // Sets up the form for editing with information from the passed appointment object.
        customerNameComboBox.setValue(appt.getCustomerName());
        assignedToChoiceBox.setValue(appt.getUserName());
        titleTextField.setText(appt.getTitle());
        descriptionTextField.setText(appt.getDescription());
        locationChoiceBox.setValue(appt.getLocation());
        contactTextField.setText(appt.getContact());
        urlTextField.setText(appt.getUrl());
        typeChoiceBox.setValue(appt.getType());
        dateDatePicker.setValue(appt.getStart().toLocalDate());
        startTimeTextField.setText(DateTimeConverter.getFormattedTimeStringFromLocalDateTime(appt.getStart()));
        endTimeTextField.setText(DateTimeConverter.getFormattedTimeStringFromLocalDateTime(appt.getEnd()));

        // Assigns the passed in object appt to the appointmentToUpdate object for editing and inserting later.
        this.appointmentToUpdate = appt;
    }

    // This is a necessary method to set null values to "" so that the database will save them (only required on update, not insertion).
    private String setStringToBlankIfNull(String text) {
        if (text != null) {
            return text;
        } else {
            return "";
        }

    }

    private void updateAppointment(Appointment appt) {

        // And due to some weird limitation by the database, null url, description, and contact are allowed on insertions, but not on updates.
        // So, must input blank text string to make it work.
        String desc = setStringToBlankIfNull(descriptionTextField.getText());
        String url = setStringToBlankIfNull(urlTextField.getText());
        String contact = setStringToBlankIfNull(contactTextField.getText());

        appt.setUserId(userDAO.getUserId(assignedToChoiceBox.getValue()));
        appt.setCustomerId(customerDAO.getCustomerId(customerNameComboBox.getValue()));
        appt.setTitle(titleTextField.getText());
        appt.setDescription(desc);
        appt.setLocation(locationChoiceBox.getValue());
        appt.setType(typeChoiceBox.getValue());
        appt.setContact(contact);
        appt.setUrl(url);

        // First the start and end datetimes are built from the date picker and text entered in the start and end time fields.
        // Then the built LocalDateTime is converted to UTC for saving in the database.
        appt.setStart(DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(LocalDateTime.of(dateDatePicker.getValue(), LocalTime.parse(startTimeTextField.getText() + ":00"))));
        appt.setEnd(DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(LocalDateTime.of(dateDatePicker.getValue(), LocalTime.parse(endTimeTextField.getText() + ":00"))));

        appointmentDAO.update(appt);

    }

    private void saveNewAppointment() throws ParseException {

        Appointment appt = new Appointment();
        appt.setCustomerId(customerDAO.getCustomerId(customerNameComboBox.getValue()));
        appt.setUserId(userDAO.getUserId(assignedToChoiceBox.getValue()));
        appt.setUserName(assignedToChoiceBox.getValue());
        appt.setTitle(titleTextField.getText());
        appt.setDescription(descriptionTextField.getText());
        appt.setLocation(locationChoiceBox.getValue());
        appt.setType(typeChoiceBox.getValue());
        appt.setContact(contactTextField.getText());
        appt.setUrl(urlTextField.getText());

        // First the start and end datetimes are built from the date picker and the text entered in the start and end time fields.
        // Then the built LocalDateTime is converted to UTC for saving in the database.
        appt.setStart(DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(LocalDateTime.of(dateDatePicker.getValue(), LocalTime.parse(startTimeTextField.getText() + ":00"))));
        appt.setEnd(DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(LocalDateTime.of(dateDatePicker.getValue(), LocalTime.parse(endTimeTextField.getText() + ":00"))));

        appointmentDAO.insert(appt);

    }

    /* Massive validation check that verifies the following:
    *  Is text entered in the requried fields? (customer name, assigned to, title, date, start, end.
    *  Start and time format
    *  Operating Hours
    *  Operating Day Of Week
    *  Can't create appointments in the past
    *  Can't set end time equal to or before start time
    */
    private boolean canDataBeSaved() {

        // Checks to see if all required text is entered.
        try {
            String[] textFields = new String[]{
                customerNameComboBox.getValue(),
                assignedToChoiceBox.getValue(),
                titleTextField.getText(),
                dateDatePicker.getValue().toString(),
                startTimeTextField.getText(),
                endTimeTextField.getText()};

            if (Validator.textIsEntered(textFields)) {
                // Intentionally left blank. If any of the fields are null, then the code falls through to the catch block and handled there.
            }

            // Checks if the customer field is blank. If it is, warn the user.
            if (customerNameComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "You must choose an existing or enter a new customer prior to saving.");
                alert.showAndWait();
                return false;
            }
            
            // Ensures the time is in the correct format. 
            if (!Validator.timeIsInCorrectFormat(startTimeTextField.getText()) || !Validator.timeIsInCorrectFormat(endTimeTextField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The start and end time must be entered in 24-hour format with a leading '0'. (i.e. '08:15')");
                alert.showAndWait();
                return false;
            }

            // Lambda that checks if the time is within operating hours.
            I_Validator isTimeWithinOperatingHours = (String start, String end)
                    -> !(LocalTime.parse(start + ":00").isBefore(DataProvider.OPENING_TIME) || LocalTime.parse(end + ":00").isAfter(DataProvider.CLOSING_TIME));

            // Ensures the time is within operating hours (07:00 - 19:00)
            if (!isTimeWithinOperatingHours.validate(startTimeTextField.getText(), endTimeTextField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The start and end time must be within the office hours. ("
                        + DataProvider.OPENING_TIME + " and " + DataProvider.CLOSING_TIME + ").");
                alert.showAndWait();
                return false;
            }

            // Lambda that checks if the start time is before the end time, or if they're equal
            I_Validator isStartTimeBeforeEndTime = (String start, String end)
                    -> !(LocalTime.parse(end + ":00").isBefore(LocalTime.parse(start + ":00")) || LocalTime.parse(end + ":00").equals(LocalTime.parse((start + ":00"))));

            // Ensures the date selected is a weekday (operating days are Monday through Friday
            if (!Validator.dateIsWeekday(dateDatePicker.getValue().getDayOfWeek())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The valid days of operation are Monday Through Friday. "
                        + "The date selected is on a " + dateDatePicker.getValue().getDayOfWeek() + ".");
                alert.showAndWait();
                return false;
            }
            // Ensures the start time is before the end time (and also not equal)
            if (!isStartTimeBeforeEndTime.validate(startTimeTextField.getText(), endTimeTextField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The start time entered is before the end time entered. Please correct in order to save.");
                alert.showAndWait();
                return false;
            }
            
            
            // Ensures the user can't create an appointment that overlaps.
            if (appointmentDAO.queryForDoAppointmentsOverlapForUser(assignedToChoiceBox.getValue(),
                    DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(LocalDateTime.of(dateDatePicker.getValue(), LocalTime.parse(startTimeTextField.getText() + ":00"))),
                    DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(LocalDateTime.of(dateDatePicker.getValue(), LocalTime.parse(endTimeTextField.getText() + ":00"))))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This appointment overlaps with an existing appointment. Please change the date/time range and try again.");
                alert.showAndWait();
                return false;
            }

            // Ensures the user can't create an appointment in the past.
            if (!Validator.dateIsAfterCurrentDate(dateDatePicker.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The date selected is in the past and cannot be selected for an appointment.");
                alert.showAndWait();
                return false;
            } else {
                return true;
            }
            


            //This is the catch from the first validation. If the customer name is null, it will fall through to this catch.
        } catch (NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "At a minimum, you must have the following entered to save:\n"
                    + "Customer Name\n"
                    + "Assigned User\n"
                    + "Title\n"
                    + "Location\n"
                    + "Type\n"
                    + "Date\n"
                    + "Start Time\n"
                    + "End Time");
            alert.showAndWait();
            System.out.println(ex.getMessage());
            return false;
        }

    }

    // Listener and alert to verify time is input in the correct format
    private void setupListenerAndAlerts(TextField control) {
        control.focusedProperty().addListener((a, b, focused) -> {
            if (!focused) {
                if (!Validator.timeIsInCorrectFormat(control.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The time has not been input in the correct format. Please ensure the format is in "
                            + "24 hour format (i.e. 07:00)");
                    alert.showAndWait();
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerNameComboBox.setItems(customerDAO.queryAllCustomers());
        assignedToChoiceBox.setItems(userDAO.queryAllActiveUsersForComboBox());
        assignedToChoiceBox.setValue(DataProvider.getCurrentUser());
        locationChoiceBox.setItems(DataProvider.LOCATIONS);
        locationChoiceBox.setValue(DataProvider.LOCATIONS.get(0));
        typeChoiceBox.setItems(DataProvider.APPOINTMENT_TYPES);
        typeChoiceBox.setValue(DataProvider.APPOINTMENT_TYPES.get(0));

        // These listeners are used to provide instant feedback on the time formats, prior to saving.
        // Even if they're ignored, save will not happen if the formats are not correct.
        setupListenerAndAlerts(startTimeTextField);
        setupListenerAndAlerts(endTimeTextField);
    }
}
