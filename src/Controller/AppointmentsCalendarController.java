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
import Log.Logger;
import Model.Appointment;
import Utilities.DataProvider;
import Utilities.DatabaseConnector;
import Utilities.Navigator;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
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
    private TableColumn<Appointment, Date> dateColumnTableView;

    @FXML
    private TableColumn<Appointment, Date> startColumnTableView;

    @FXML
    private TableColumn<Appointment, Date> endColumnTableView;

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
        AppointmentDetailController.isEditing = false;
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(DataProvider.pathOfFXML.APPOINTMENT_DETAIL.getPath())));
    }

    @FXML
    void onActionBack(ActionEvent event) throws IOException, SQLException {
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(DataProvider.pathOfFXML.DASHBOARD.getPath())));
    }

    @FXML
    void onActionDeleteAppointment(ActionEvent event) {

    }

    @FXML
    void onActionSearch(ActionEvent event) {

    }

    @FXML
    void onActionEditAppointment(ActionEvent event) {

        AppointmentDetailController.isEditing = true;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(DataProvider.pathOfFXML.APPOINTMENT_DETAIL.getPath()));
            loader.load();

            AppointmentDetailController.previousPath = DataProvider.pathOfFXML.APPOINTMENTS_CALENDAR.getPath();

            AppointmentDetailController apptController = loader.getController();
            apptController.sendAppointmentDetails(calendarAppointmentTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
            Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(DataProvider.pathOfFXML.APPOINTMENT_DETAIL.getPath())));

        } catch (IOException | NullPointerException | SQLException ex) {
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
            appointments = appointmentDAO.queryForAppointmentCalendar();
        } else {
            appointments = appointmentDAO.queryForAppointmentCalendar();
        }

        calendarAppointmentTableView.setItems(appointments);
        customerColumnTableView.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        assignedToColumnTableView.setCellValueFactory(new PropertyValueFactory<>("userName"));
        titleColumnTableView.setCellValueFactory(new PropertyValueFactory<>("title"));
        locationColumnTableView.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumnTableView.setCellValueFactory(new PropertyValueFactory<>("contact"));
        dateColumnTableView.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        startColumnTableView.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumnTableView.setCellValueFactory(new PropertyValueFactory<>("end"));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshData();
    }

}
