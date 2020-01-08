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

import DAO.*;
import Model.Address;
import Model.City;
import Model.Country;
import Model.Customer;
import Utilities.DataProvider;
import Utilities.DatabaseConnector;
import Utilities.Navigator;
import Utilities.Validator;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 *
 * @author Jedidiah May
 */
public class CreateEditCustomerController implements Initializable {

    DatabaseConnector dc = new DatabaseConnector();
    Connection conn;

    CustomerDAO customerDAO;
    CityDAO cityDAO;
    CountryDAO countryDAO;
    AddressDAO addressDAO;

    private int addressId, cityId, countryId;
    
    static String previousPath;
    static boolean isEditing;
    static ObservableList<String> cities; // If editing, populates the cityComboBox for the country already set

    Customer customerToUpdate = new Customer();

    // Constructor
    public CreateEditCustomerController() throws IOException {
        try {
            conn = dc.createConnection();
            this.addressDAO = new AddressDAO(conn);
            this.customerDAO = new CustomerDAO(conn);
            this.cityDAO = new CityDAO(conn);
            this.countryDAO = new CountryDAO(conn);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="FXML objects">
    @FXML
    private TextField address2TextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private ComboBox<String> cityComboBox;

    @FXML
    private CheckBox activeCheckBox;
    // </editor-fold>

    // Cancel - go back to the previous screen.
    @FXML
    void onActionCancel(ActionEvent event) throws IOException, SQLException {
        conn.close();
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(previousPath)));
    }

    // When a country is selected, city options will be available, using the country as a 'where' clause
    // in the SQL statement.
    @FXML
    void onActionCountrySelected(ActionEvent event) {
        cityComboBox.setValue("");
        if (countryComboBox.getValue().isEmpty()) {
            cityComboBox.setDisable(true);
            return;
        }
        cityComboBox.setDisable(false);
        cityComboBox.setItems(cityDAO.queryCities(countryComboBox.getValue()));
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {

        // Data validation
        if (!canDataBeSaved()) {
            return;
        }

        if (!countryDAO.doesCountryExist(countryComboBox.getValue())) {
            saveNewCountry();
        }
        // Whether country existed or not, it does after the logic above, and a country Id can be retrieved.
        this.countryId = countryDAO.getCountryId(countryComboBox.getValue());

        if (!cityDAO.doesCityExist(cityComboBox.getValue(), this.countryId)) {
            saveNewCity();
        }
        // Whether or not the city existed before, it does now. Retrieve city Id.
        this.cityId = cityDAO.getCityId(cityComboBox.getValue(), this.countryId);

        if (isEditing) {
            // At this point, the country Id and city Id is assigned to the fields.
            // If updating, retrieve the addressId from the customerToUpdate object.
            int addrId = this.customerToUpdate.getAddressId();
            this.addressId = addrId;
            // The address will not be new - has to exist, so it will be an update, based on the addressId.
            updateAddress(addressDAO.getAddress(addrId));
            updateExistingCustomer(this.customerToUpdate);
        } else {
            // If not editing/updating, then this is a new customer. That means creating a new address, 
            // getting the Id for the newly created address, and then assigning it, along with the customer
            // properties to the new customer object and inserting that into the database.
            saveNewAddress();
            this.addressId = addressDAO.getMostRecentAddressEntered();
            saveNewCustomer();
        }
        conn.close();
        Navigator.displayScreen(event, FXMLLoader.load(getClass().getResource(previousPath)));
    }

    // Validation to make sure that data can be saved and doesn't violate any rules.
    private boolean canDataBeSaved() {

        //Setup the string array that holds the text fields to verify that they are not empty.
        String[] textFields = new String[]{
            customerNameTextField.getText(),
            addressTextField.getText(),
            cityComboBox.getValue(),
            countryComboBox.getValue(),
            postalCodeTextField.getText()};

        if (!Validator.textIsEntered(textFields)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "At a minimum, you must have a Customer Name, Address, City, Country, and Postal Code entered to save.");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    // Retrives a customer object from the previous screen (manage customers) to populate data on this screen,
    // as well as assigning a customer ojbect to manipulate to the field above.
    void sendCustomerDetails(Customer customer) {
        //populate fields
        customerNameTextField.setText(customer.getCustomerName());
        addressTextField.setText(customer.getAddress());
        address2TextField.setText(customer.getAddress2());
        cityComboBox.setValue(customer.getCity());
        cityComboBox.setItems(cityDAO.queryCities(customer.getCountry()));
        countryComboBox.setValue(customer.getCountry());
        postalCodeTextField.setText(customer.getPostalCode());
        phoneTextField.setText(customer.getPhone());
        activeCheckBox.setSelected(customer.getActive());

        //Creates a customer object available for editing.
        this.customerToUpdate = customer;
    }

    /* 
    *  Obviously, this only takes place when updating an existing customer.
    *  Injecting customerToUpdate, which should have addressId, cityId, and countryId.
    *  Address Id will never change. if a new country or city is being created as a result
    *  of updating, then new Id's will have to be associated.
    */ 
    private void updateExistingCustomer(Customer custToUpdate) {

        custToUpdate.setCustomerName(customerNameTextField.getText());
        custToUpdate.setAddressId(this.addressId);
        custToUpdate.setActive(activeCheckBox.isSelected());
        custToUpdate.setLastUpdateBy(DataProvider.getCurrentUser());

        customerDAO.update(custToUpdate);

    }

    // Obviously, this only takes place when creating a new customer.
    // A new address has to be created first. And possibly, a new city and country as well.
    private void saveNewCustomer() {

        Customer cust = new Customer();
        cust.setCustomerName(customerNameTextField.getText());
        cust.setAddressId(this.addressId);
        cust.setActive(activeCheckBox.isSelected());
        cust.setCityId(this.cityId);
        cust.setCountryId(this.countryId);
        
        customerDAO.insert(cust);
    }

    /* 
    *  This can take place whether editing or creating a new customer.
    *  Validation should check to see if the customer/country combination exists.
    *  Country is checked first. If it doesn't exist, then that country is created in the database.
    *  If country/city combo doesn't exist, then a record is created in the database with that combo.
    */ 
    private void saveNewCity() {
        City city = new City();
        city.setCity(cityComboBox.getValue());
        city.setCountryId(this.countryId);
        
        cityDAO.insert(city);
    }

    private void saveNewCountry() {

        Country country = new Country();
        country.setCountry(countryComboBox.getValue());

        countryDAO.insert(country);
    }

    // This is only called if editing an existing customer. Address object passed here
    // is retrieved from addressId, which is from the customerToUpdate object.
    private void updateAddress(Address addressToUpdate) throws IOException {

        addressToUpdate.setAddressId(this.addressId);
        addressToUpdate.setAddress(addressTextField.getText());
        addressToUpdate.setAddress2(address2TextField.getText());
        addressToUpdate.setCityId(this.cityId);
        addressToUpdate.setPostalCode(postalCodeTextField.getText());
        addressToUpdate.setPhone(phoneTextField.getText());
        
        addressDAO.update(addressToUpdate);

    }

    // This takes place only when creating a new customer.
    private void saveNewAddress() throws IOException {

        Address address = new Address();
        address.setAddress(addressTextField.getText());
        address.setAddress2(address2TextField.getText());
        address.setCityId(this.cityId);
        address.setPostalCode(postalCodeTextField.getText());
        address.setPhone(phoneTextField.getText());

        addressDAO.insert(address);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        countryComboBox.setItems(countryDAO.queryAllCountries());
        if (isEditing) {
            cityComboBox.setDisable(false);
        } else {
            cityComboBox.setDisable(true);
        }
    }
}
