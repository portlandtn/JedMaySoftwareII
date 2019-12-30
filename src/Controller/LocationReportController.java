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
import Utilities.DataProvider;
import Utilities.Navigator;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
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
public class LocationReportController implements Initializable {

    @FXML
    private ChoiceBox<String> locationChoiceBox;

    @FXML
    private TableView<Appointment> appointmentUserReportTableView;

    @FXML
    private TableColumn<Appointment, String> titleColumnTableView;

    @FXML
    private TableColumn<Appointment, String> typeColumnTableView;

    @FXML
    private TableColumn<Appointment, String> contactColumnTableView;

    @FXML
    private TableColumn<Appointment, Date> dateColumnTableView;

    @FXML
    private TableColumn<Appointment, Date> startColumnTableView;

    @FXML
    private TableColumn<Appointment, Date> endColumnTableView;

    @FXML
    void onActionGoBack(ActionEvent event) throws IOException, SQLException {
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(DataProvider.pathOfFXML.REPORTS_DASHBOARD.getPath())));
    }

    @FXML
    void onActionMoreInfo(ActionEvent event) throws IOException, SQLException {
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(DataProvider.pathOfFXML.CREATE_EDIT_APPOINTMENT.getPath())));
    }

    @FXML
    void onActionPrintReport(ActionEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
