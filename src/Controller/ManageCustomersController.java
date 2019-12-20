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
import Utilities.Navigator;
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
    Connection conn;
    static String previousPath;

    // <editor-fold defaultstate="collapsed" desc="FXML objects">
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
    // </editor-fold>

    // Constructor
    public ManageCustomersController() {
        try {
            conn = dc.createConnection();
            this.customerDAO = new CustomerDAO(conn);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Active radio button selected, show only active customers.
    @FXML
    void onActionActiveSelected(ActionEvent event) {
        searchTextField.setText("");
        refreshData();
    }

    // All radio button selected, show all users (whether active or inactive).
    @FXML
    void onActionAllSelected(ActionEvent event) {
        searchTextField.setText("");
        refreshData();
    }

    // Inactive radio button selected, show only inactive customers.
    @FXML
    void onActionInactiveSelected(ActionEvent event) {
        searchTextField.setText("");
        refreshData();
    }

    // Search button clicked.
    @FXML
    void onActionSearch(ActionEvent event) {
        
        // Holds the list of retrieved items
        ObservableList<Customer> customerSearchResultsList = FXCollections.observableArrayList();

        String searchText = searchTextField.getText();
        
        // If the textbox is empty when searching, display all customers.
        if (searchText.isEmpty()) {
            manageCustomersTableView.setItems(customerDAO.query());
            return;
        }

        customerSearchResultsList.clear();

        // Checks to see if the search string is only a number. If it is a number, it's going to lookup
        // the customer Id, rather than the customer name.
        if (Validator.isSearchStringNumber(searchTextField.getText())) {
            customerSearchResultsList = customerDAO.lookupCustomer(Integer.parseInt(searchText));

        } else {
            // If the search string has letters, then the program looks up the customer name.
            customerSearchResultsList = customerDAO.lookupCustomer(searchText);
        }
        
        // If the search yields no results, then pop-up a box telling the user why it's blank.
        if (customerSearchResultsList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "A customer was not found.");
            alert.showAndWait();
        } else {
            // If it's not empty, show the list.
            manageCustomersTableView.setItems(customerSearchResultsList);
        }
    }

    //Go Back button
    @FXML
    void onActionShowDashboard(ActionEvent event) throws IOException, SQLException {
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml")));
    }

    @FXML
    void onActionAddNewCustomer(ActionEvent event) throws IOException, SQLException {
        CreateEditCustomerController.isEditing = false;
        CreateEditCustomerController.previousPath = "/View/ManageCustomers.fxml";
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource("/View/CreateEditCustomer.fxml")));

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

    // If editing a customer, this sets up the form on the next screen with data.
    @FXML
    void onActionEditCustomer(ActionEvent event) throws IOException {
        
        // Static field that guides code.
        CreateEditCustomerController.isEditing = true;
        
        // Setups the controller.
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/CreateEditCustomer.fxml"));
            loader.load();

            // When the back button is pressed, this tells it where to go on the next screen (static field)
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

    // This sets up the table. When radio buttons are selected, or when the form is initialized, this is called.
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshData();
    }

}
