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
import DAO.UserDAO;
import Model.Appointment;
import Utilities.DatabaseConnector;
import Utilities.Navigator;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Jedidiah May
 */
public class ScheduleReportController implements Initializable {

    DatabaseConnector dc = new DatabaseConnector();
    Connection conn;
    AppointmentDAO appointmentDAO;
    UserDAO userDAO;

    // Constructor
    public ScheduleReportController() {
        try {
            this.conn = dc.createConnection();
            this.appointmentDAO = new AppointmentDAO(conn);
            this.userDAO = new UserDAO(conn);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="FXML Objects">
    @FXML
    private ChoiceBox<String> userChoiceBox;

    @FXML
    private TableView<Appointment> appointmentUserReportTableView;

    @FXML
    private TableColumn<Appointment, String> titleColumnTableView;

    @FXML
    private TableColumn<Appointment, String> typeColumnTableView;

    @FXML
    private TableColumn<Appointment, String> contactColumnTableView;

    @FXML
    private TableColumn<Appointment, LocalDateTime> dateColumnTableView;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startColumnTableView;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endColumnTableView;

    // <editor-fold desc="Standard FXML Methods">
    @FXML
    void onActionGoBack(ActionEvent event) throws IOException, SQLException {
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(Navigator.pathOfFXML.REPORTS_DASHBOARD.getPath())));
    }
    // </editor-fold>
    
    private void refreshData(String user) {

        ObservableList<Appointment> appointments = appointmentDAO.queryForAppointmentByUser(user);

        //Setup the appointment table with data from the database, based on the view selected.
        appointmentUserReportTableView.setItems(appointments);
        typeColumnTableView.setCellValueFactory(new PropertyValueFactory<>("type"));
        titleColumnTableView.setCellValueFactory(new PropertyValueFactory<>("title"));
        contactColumnTableView.setCellValueFactory(new PropertyValueFactory<>("contact"));
        startColumnTableView.setCellValueFactory(new PropertyValueFactory<>("start"));

        // To format the columns appropriately, used Lambdas to override the updateItem method of CellFactory with the new format. 
        // This was done for the three datetime columns below.
        startColumnTableView.setCellFactory(column -> {
            TableCell<Appointment, LocalDateTime> cell = new TableCell<Appointment, LocalDateTime>() {
                private SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(Date.from(item.atZone(ZoneId.systemDefault()).toInstant())));

                    }
                }
            };
            return cell;
        });

        endColumnTableView.setCellValueFactory(new PropertyValueFactory<>("end"));
        endColumnTableView.setCellFactory(column -> {
            TableCell<Appointment, LocalDateTime> cell = new TableCell<Appointment, LocalDateTime>() {
                private SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(Date.from(item.atZone(ZoneId.systemDefault()).toInstant())));

                    }
                }
            };
            return cell;
        });

        dateColumnTableView.setCellValueFactory(new PropertyValueFactory<>("start"));

        dateColumnTableView.setCellFactory(column -> {
            TableCell<Appointment, LocalDateTime> cell = new TableCell<Appointment, LocalDateTime>() {
                private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(Date.from(item.atZone(ZoneId.systemDefault()).toInstant())));

                    }
                }
            };
            return cell;
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userChoiceBox.setItems(userDAO.queryAllActiveUsersForComboBox());
        // ChocieBox listener with a lambda overriding the Listener abstract method (calling to refresh the table with the newly selected value)
        userChoiceBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            refreshData(newValue);
        });
    }

}
