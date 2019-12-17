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
            this.customerDAO = new CustomerDAO(dc.createConnection());
            this.cityDAO = new CityDAO(dc.createConnection());
            this.countryDAO = new CountryDAO(dc.createConnection());
            this.addressDAO = new AddressDAO(dc.createConnection());
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
    void onActionCancel(ActionEvent event) throws IOException {
        displayScreen(previousPath, event);
    }
    
    @FXML
    void onActionCountrySelected(ActionEvent event) {
        cityComboBox.setValue("");
        if (countryComboBox.getValue().isEmpty()){
            cityComboBox.setDisable(true);
            return;
        }
        cityComboBox.setDisable(false);
        cityComboBox.setItems(cityDAO.queryCities(countryComboBox.getValue()));
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        if (!canDataBeSaved()) {
            return;
        }

        if (isEditing) {
            updateExistingCustomer(this.customerToUpdate);
        } else {
            saveNewCustomer();
        }
        displayScreen(previousPath, event);
    }

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

    private void displayScreen(String path, ActionEvent event) throws IOException {

        Stage stage;
        Parent scene;

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    void sendCustomerDetails(Customer customer) {
        customerNameTextField.setText(customer.getCustomerName());
        addressTextField.setText(customer.getAddress());
        address2TextField.setText(customer.getAddress2());
        cityComboBox.setValue(customer.getCity());
        countryComboBox.setValue(customer.getCountry());
        postalCodeTextField.setText(customer.getPostalCode());
        phoneTextField.setText(customer.getPhone());
        activeCheckBox.setSelected(customer.getActive());
        this.customerToUpdate = customer;
    }

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

    private void updateExistingCustomer(Customer customerToUpdate) {

        try (Connection conn = dc.createConnection()) {

            if (!countryDAO.doesCountryExist(country)) {
                saveNewCountry();
            }

            if (!cityDAO.doesCityExist(cityComboBox.getValue(), this.countryId)) {
                saveNewCity();
            }
            
            updateAddress();
            
            customerToUpdate.setCustomerName(customerNameTextField.getText());
            customerToUpdate.setAddressId(this.addressId);       
            customerToUpdate.setActive(activeCheckBox.isSelected());
            customerToUpdate.setLastUpdate(DataProvider.getCurrentDate());
            customerToUpdate.setLastUpdateBy(DataProvider.getCurrentUser());

            customerDAO.update(customerToUpdate);
            conn.close();

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void saveNewCustomer() {

        setVariablesFromScreen();

        try (Connection conn = dc.createConnection()) {
            setVariablesFromScreen();

            if (!countryDAO.doesCountryExist(country)) {
                saveNewCountry();
            }

            if (!cityDAO.doesCityExist(cityComboBox.getValue(), this.countryId)) { //Where is the couuntry ID coming from?
                saveNewCity();
            }

            Customer cust = new Customer();
            cust.setCustomerName(this.customerName);
            cust.setActive(this.active);
            cust.setCreateDate(this.createDate);
            cust.setCreatedBy(this.createdBy);
            cust.setLastUpdate(this.lastUpdate);
            cust.setLastUpdateBy(this.lastUpdateBy);
            customerDAO.insert(cust);
            conn.close();

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void saveNewCity() {
        try (Connection conn = dc.createConnection()) {

            City city = new City();
            city.setCity(cityComboBox.getValue());
            city.setCountryId(this.countryId);
            city.setCreateDate(DataProvider.getCurrentDate());
            city.setCreatedBy(DataProvider.getCurrentUser());
            city.setLastUpdate(DataProvider.getCurrentDate());
            city.setLastUpdateBy(DataProvider.getCurrentUser());

            cityDAO.insert(city);
            conn.close();

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void saveNewCountry() {
        try (Connection conn = dc.createConnection()) {

            Country country = new Country();
            country.setCountry(countryComboBox.getValue());
            country.setCreateDate(DataProvider.getCurrentDate());
            country.setCreatedBy(DataProvider.getCurrentUser());
            country.setLastUpdate(DataProvider.getCurrentDate());
            country.setLastUpdateBy(DataProvider.getCurrentUser());

            countryDAO.insert(country);
            
            this.countryId = countryDAO.getCountryId(countryComboBox.getValue());
            conn.close();

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void updateAddress(){
        try (Connection conn = dc.createConnection()){
            
            Address address = new Address();
            address.setAddressId(this.addressId);
            address.setAddress(addressTextField.getText());
            address.setAddress2(address2TextField.getText());
            address.setCityId(this.cityId);
            address.setPhone(phoneTextField.getText());
            address.setPostalCode(postalCodeTextField.getText());
            
            addressDAO.update(address);
            
            conn.close();
            
            
        } catch (SQLException | ClassNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        countryComboBox.setItems(countryDAO.queryAllCountries());
    }
}
