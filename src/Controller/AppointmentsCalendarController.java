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
import Utilities.Navigator;
import Utilities.Validator;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Jedidiah May
 */
public class AppointmentsCalendarController implements Initializable {

    DatabaseConnector dc = new DatabaseConnector();
    Connection conn;
    AppointmentDAO appointmentDAO;

    // <editor-fold defaultstate="collapsed" desc="FXML objects">
    @FXML
    private RadioButton allRadioButton;

    @FXML
    private ToggleGroup CalendarViewToggle;

    @FXML
    private RadioButton monthRadioButton;

    @FXML
    private RadioButton weekRadioButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Appointment> calendarAppointmentTableView;

    @FXML
    private TableColumn<Appointment, String> customerColumnTableView;

    @FXML
    private TableColumn<Appointment, String> assignedToColumnTableView;

    @FXML
    private TableColumn<Appointment, String> titleColumnTableView;

    @FXML
    private TableColumn<Appointment, String> locationColumnTableView;

    @FXML
    private TableColumn<Appointment, String> contactColumnTableView;

    @FXML
    private TableColumn<Appointment, LocalDateTime> dateColumnTableView;
    

    @FXML
    private TableColumn<Appointment, LocalDateTime> startColumnTableView;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endColumnTableView;

    // </editor-fold>
    public AppointmentsCalendarController() {
        try {
            this.conn = dc.createConnection();
            this.appointmentDAO = new AppointmentDAO(conn);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException, SQLException {
        CreateEditAppointmentController.isEditing = false;
        CreateEditAppointmentController.previousPath = Navigator.pathOfFXML.APPOINTMENTS_CALENDAR.getPath();
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(Navigator.pathOfFXML.CREATE_EDIT_APPOINTMENT.getPath())));
    }

    @FXML
    void onActionBack(ActionEvent event) throws IOException, SQLException {
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(Navigator.pathOfFXML.DASHBOARD.getPath())));
    }

    @FXML
    void onActionDeleteAppointment(ActionEvent event) {
        appointmentDAO.remove(calendarAppointmentTableView.getSelectionModel().getSelectedItem().getAppointmentId());
        refreshData();
    }

    @FXML
    void onActionSearch(ActionEvent event) {
        ObservableList<Appointment> appointmentSearchResultsList = FXCollections.observableArrayList();

        String searchText = searchTextField.getText();

        if (searchText.isEmpty()) {
            calendarAppointmentTableView.setItems(appointmentDAO.query());
            return;
        }

        appointmentSearchResultsList.clear();

        if (Validator.isSearchStringNumber(searchTextField.getText())) {
            appointmentSearchResultsList = appointmentDAO.lookupAppointment(Integer.parseInt(searchText));

        } else {
            appointmentSearchResultsList = appointmentDAO.lookupAppointment(searchText);
        }

        if (appointmentSearchResultsList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No appointment was not found.");
            alert.showAndWait();
        } else {
            calendarAppointmentTableView.setItems(appointmentSearchResultsList);
        }
    }

    @FXML
    void onActionEditAppointment(ActionEvent event) {

        CreateEditAppointmentController.isEditing = true;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Navigator.pathOfFXML.CREATE_EDIT_APPOINTMENT.getPath()));
            loader.load();

            CreateEditAppointmentController.previousPath = Navigator.pathOfFXML.APPOINTMENTS_CALENDAR.getPath();

            CreateEditAppointmentController apptController = loader.getController();
            apptController.sendAppointmentDetails(calendarAppointmentTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (IOException | NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void onActionSelectAll(ActionEvent event) {
        refreshData();
    }

    @FXML
    void onActionSelectMonth(ActionEvent event) {
        refreshData();
    }

    @FXML
    void onActionSelectWeek(ActionEvent event) {
        refreshData();
    }

    private void refreshData() {
        ObservableList<Appointment> appointments;

        //Setup the appointment table with data from the database.
        if (allRadioButton.isSelected()) {
            appointments = appointmentDAO.queryForAppointmentCalendar();
        } else if (monthRadioButton.isSelected()) {
            appointments = appointmentDAO.queryForAppointmentCalendarMonthly();
        } else {
            appointments = appointmentDAO.queryForAppointmentCalendarWeekly();
        }

        calendarAppointmentTableView.setItems(appointments);
        customerColumnTableView.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        assignedToColumnTableView.setCellValueFactory(new PropertyValueFactory<>("userName"));
        titleColumnTableView.setCellValueFactory(new PropertyValueFactory<>("title"));
        locationColumnTableView.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumnTableView.setCellValueFactory(new PropertyValueFactory<>("contact"));
        //dateColumnTableView.setCellValueFactory(cellData -> cellData.getValue().getAppointmentDate().); 
//        dateColumnTableView.setCellFactory(col -> new TableCell<Appointment, LocalDateTime>() {
//            @Override
//            protected void updateItem(LocalDateTime item, boolean empty) {
//                        super.updateItem(item, empty);
//        if (empty)
//            setText(null);
//        else
//            setText(String.format(item.format(formatter)));
//            }
//        }
        dateColumnTableView.setCellValueFactory(new PropertyValueFactory<>("start"));
        startColumnTableView.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumnTableView.setCellValueFactory(new PropertyValueFactory<>("end"));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshData();
    }

}
