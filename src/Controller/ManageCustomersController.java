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

import DAO.CustomerDAO;
import Model.Customer;
import Utilities.DatabaseConnector;
import Utilities.Validator;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Jedidiah May
 */
public class ManageCustomersController implements Initializable {

    DatabaseConnector dc = new DatabaseConnector();
    CustomerDAO customerDAO;
    static String previousPath;

    @FXML
    private RadioButton allRadioButton;

    @FXML
    private ToggleGroup activeInactiveRadioButtons;

    @FXML
    private RadioButton activeRadioButton;

    @FXML
    private RadioButton inactiveRadioButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Customer> manageCustomersTableView;

    @FXML
    private TableColumn<Customer, Integer> idColumnTableView;

    @FXML
    private TableColumn<Customer, String> nameColumnTableView;

    @FXML
    private TableColumn<Customer, String> activeColumnTableView;

    @FXML
    private TableColumn<Customer, String> addressColumnTableView;

    @FXML
    private TableColumn<Customer, String> address2ColumnTableView;

    @FXML
    private TableColumn<Customer, String> cityColumnTableView;

    @FXML
    private TableColumn<Customer, String> zipCodeColumnTableView;

    @FXML
    private TableColumn<Customer, String> countryColumnTableView;

    @FXML
    private TableColumn<Customer, String> phoneColumnTableView;

    public ManageCustomersController() {
        try {
            this.customerDAO = new CustomerDAO(dc.createConnection());
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void onActionActiveSelected(ActionEvent event) {
        searchTextField.setText("");
        refreshData();
    }

    @FXML
    void onActionAllSelected(ActionEvent event) {
        searchTextField.setText("");
        refreshData();
    }

    @FXML
    void onActionInactiveSelected(ActionEvent event) {
        searchTextField.setText("");
        refreshData();
    }

    @FXML
    void onActionSearch(ActionEvent event) {
        ObservableList<Customer> customerSearchResultsList = FXCollections.observableArrayList();

        String searchText = searchTextField.getText();

        if (searchText.isEmpty()) {
            manageCustomersTableView.setItems(customerDAO.query());
            return;
        }

        customerSearchResultsList.clear();

        if (Validator.isSearchStringNumber(searchTextField.getText())) {
            customerSearchResultsList = customerDAO.lookupCustomer(Integer.parseInt(searchText));

        } else {
            customerSearchResultsList = customerDAO.lookupCustomer(searchText);
        }

        if (customerSearchResultsList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "A customer was not found.");
            alert.showAndWait();
        } else {
            manageCustomersTableView.setItems(customerSearchResultsList);
        }
    }

    //Go Back button
    @FXML
    void onActionShowDashboard(ActionEvent event) throws IOException {
        displayScreen("/View/Dashboard.fxml", event);
    }

    @FXML
    void onActionAddNewCustomer(ActionEvent event) throws IOException {
        CreateEditCustomerController.isEditing = false;
        CreateEditCustomerController.previousPath = "/View/ManageCustomers.fxml";
        displayScreen("/View/CreateEditCustomer.fxml", event);
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (!ButtonType.CANCEL.equals(result)) {
            customerDAO.remove(manageCustomersTableView.getSelectionModel().getSelectedItem().getCustomerId());
            refreshData();
        }
    }

    @FXML
    void onActionEditCustomer(ActionEvent event) throws IOException {
        CreateEditCustomerController.isEditing = true;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/CreateEditCustomer.fxml"));
            loader.load();

            CreateEditCustomerController.previousPath = "/View/ManageCustomers.fxml";

            CreateEditCustomerController custController = loader.getController();
            custController.sendCustomerDetails(manageCustomersTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a customer to edit.");
            alert.showAndWait();
        }

    }

    private void refreshData() {
        ObservableList<Customer> customers;

        if (allRadioButton.isSelected()) {
            customers = customerDAO.queryWithAddress();
        } else if (activeRadioButton.isSelected()) {
            customers = customerDAO.queryActiveInactive(true);
        } else {
            customers = customerDAO.queryActiveInactive(false);
        }

        //Setup the customer table with data from the database.
        manageCustomersTableView.setItems(customers);
        idColumnTableView.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumnTableView.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        activeColumnTableView.setCellValueFactory(new PropertyValueFactory<>("active"));
        addressColumnTableView.setCellValueFactory(new PropertyValueFactory<>("address"));
        address2ColumnTableView.setCellValueFactory(new PropertyValueFactory<>("address2"));
        cityColumnTableView.setCellValueFactory(new PropertyValueFactory<>("city"));
        zipCodeColumnTableView.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        countryColumnTableView.setCellValueFactory(new PropertyValueFactory<>("country"));
        phoneColumnTableView.setCellValueFactory(new PropertyValueFactory<>("phone"));

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
        refreshData();
    }

}
