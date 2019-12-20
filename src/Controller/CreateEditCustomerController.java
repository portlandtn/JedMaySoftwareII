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
import Utilities.Validator;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
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
public class CreateEditCustomerController implements Initializable {

    DatabaseConnector dc = new DatabaseConnector();
    Connection conn;

    CustomerDAO customerDAO;
    CityDAO cityDAO;
    CountryDAO countryDAO;
    AddressDAO addressDAO;

    static String previousPath;
    static Boolean isEditing;

    Customer customerToUpdate = new Customer();

    private String customerName, address, address2, city, country, postalCode, phone, createdBy, lastUpdateBy;
    private int customerId, addressId, cityId, countryId;
    private Date createDate, lastUpdate;
    private Boolean active;

    public CreateEditCustomerController() {
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

    @FXML
    void onActionCancel(ActionEvent event) throws IOException, SQLException {
        displayScreen(previousPath, event);
    }

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

        if (!canDataBeSaved()) {
            return;
        }

        if (!countryDAO.doesCountryExist(countryComboBox.getValue())) {
            saveNewCountry();
        }
        //Whether country existed or not, it does after the logic above, and a country Id can be retrieved.
        this.countryId = countryDAO.getCountryId(countryComboBox.getValue());

        if (!cityDAO.doesCityExist(cityComboBox.getValue(), this.countryId)) {
            saveNewCity();
        }
        //Whether or not the city existed before, it does now. Retrieve city Id.
        this.cityId = cityDAO.getCityId(cityComboBox.getValue());

        if (isEditing) {
            updateAddress(addressDAO.getAddress(this.customerToUpdate.getAddressId()));
            updateExistingCustomer(this.customerToUpdate);
        } else {
            saveNewAddress();
            this.addressId = addressDAO.getMostRecentAddressEntered();
            saveNewCustomer();
        }
        displayScreen(previousPath, event);
    }

    //simply a validator to make sure that data can be saved and doesn't violate any rules.
    private Boolean canDataBeSaved() {

        //Setup the string array that holds the text fields to verify that are not empty.
        String[] textFields = new String[]{
            customerNameTextField.getText(),
            addressTextField.getText(),
            cityComboBox.getValue(),
            countryComboBox.getValue(),
            postalCodeTextField.getText()};

        if (!Validator.isTextEntered(textFields)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "At a minimum, you must have a Customer Name, Address, City, Country, and Postal Code entered to save.");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    //Retrives a customer object from the previous screen (manage customers) to populate data on this screen.
    void sendCustomerDetails(Customer customer) {
        //populate fields
        customerNameTextField.setText(customer.getCustomerName());
        addressTextField.setText(customer.getAddress());
        address2TextField.setText(customer.getAddress2());
        cityComboBox.setValue(customer.getCity());
        countryComboBox.setValue(customer.getCountry());
        postalCodeTextField.setText(customer.getPostalCode());
        phoneTextField.setText(customer.getPhone());
        activeCheckBox.setSelected(customer.getActive());

        //Creates a customer object available for editing.
        this.customerToUpdate = customer;
    }

    //sets field variables from the values entered on the screen
    private void setVariablesFromScreen() {
        this.customerName = customerNameTextField.getText();
        this.address = addressTextField.getText();
        this.address2 = address2TextField.getText();
        this.city = cityComboBox.getValue();
        this.country = countryComboBox.getValue();
        this.postalCode = postalCodeTextField.getText();
        this.phone = phoneTextField.getText();
        this.active = activeCheckBox.isSelected();
    }

    private void updateExistingCustomer(Customer custToUpdate) {

//        //if the country does not exist, create a new country record in the country database
//        if (!countryDAO.doesCountryExist(country)) {
//            saveNewCountry();
//
//            //Assign the newly created country id to the country id field
//            this.countryId = countryDAO.getCountryId(countryComboBox.getValue());
//        }
//
//        //Checks to see if the city/country combination exists. If it does not exist, creates a record in the city database.
//        if (!cityDAO.doesCityExist(cityComboBox.getValue(), this.countryId)) {
//            saveNewCity();
//        }
//

        custToUpdate.setCustomerName(customerNameTextField.getText());
        custToUpdate.setAddressId(this.addressId);
        custToUpdate.setActive(activeCheckBox.isSelected());
        custToUpdate.setLastUpdate(DataProvider.getCurrentDate());
        custToUpdate.setLastUpdateBy(DataProvider.getCurrentUser());

        customerDAO.update(custToUpdate);

    }

    private void saveNewCustomer() {

        setVariablesFromScreen();
//
//        if (!countryDAO.doesCountryExist(country)) {
//            saveNewCountry();
//        }
//        //Whether country existed or not, it does after the logic above, and a country Id can be retrieved.
//        this.countryId = countryDAO.getCountryId(countryComboBox.getValue());
//
//        if (!cityDAO.doesCityExist(cityComboBox.getValue(), this.countryId)) {
//            saveNewCity();
//        }
//        //Whether or not the city existed before, it does now. Retrive city Id.
//        this.cityId = cityDAO.getCityId(cityComboBox.getValue());

        Customer cust = new Customer();
        cust.setCustomerName(this.customerName);
        cust.setAddressId(this.addressId);
        cust.setActive(this.active);
        cust.setCityId(this.cityId);
        cust.setCountryId(this.countryId);
        cust.setCreateDate(this.createDate);
        cust.setCreatedBy(this.createdBy);
        cust.setLastUpdate(this.lastUpdate);
        cust.setLastUpdateBy(this.lastUpdateBy);
        customerDAO.insert(cust);
    }

    private void saveNewCity() {
        City city = new City();
        city.setCity(cityComboBox.getValue());
        city.setCountryId(this.countryId);
        city.setCreateDate(DataProvider.getCurrentDate());
        city.setCreatedBy(DataProvider.getCurrentUser());
        city.setLastUpdate(DataProvider.getCurrentDate());
        city.setLastUpdateBy(DataProvider.getCurrentUser());

        cityDAO.insert(city);
    }

    private void saveNewCountry() {

        Country country = new Country();
        country.setCountry(countryComboBox.getValue());
        country.setCreateDate(DataProvider.getCurrentDate());
        country.setCreatedBy(DataProvider.getCurrentUser());
        country.setLastUpdate(DataProvider.getCurrentDate());
        country.setLastUpdateBy(DataProvider.getCurrentUser());

        countryDAO.insert(country);
    }

    private void updateAddress(Address addressToUpdate) {

        addressToUpdate.setAddressId(this.addressId);
        addressToUpdate.setAddress(addressTextField.getText());
        addressToUpdate.setAddress2(address2TextField.getText());
        addressToUpdate.setCityId(this.cityId);
        addressToUpdate.setPostalCode(postalCodeTextField.getText());
        addressToUpdate.setPhone(phoneTextField.getText());
        addressDAO.update(addressToUpdate);

    }

    private void saveNewAddress() {

        Address address = new Address();
        address.setAddressId(this.addressId);
        address.setAddress(addressTextField.getText());
        address.setAddress2(address2TextField.getText());
        address.setCityId(this.cityId);
        address.setPostalCode(postalCodeTextField.getText());
        address.setPhone(phoneTextField.getText());

        addressDAO.insert(address);
    }

    //helper method to display a screen.
    private void displayScreen(String path, ActionEvent event) throws IOException, SQLException {

        Stage stage;
        Parent scene;

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        conn.close();
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        countryComboBox.setItems(countryDAO.queryAllCountries());
    }
}
