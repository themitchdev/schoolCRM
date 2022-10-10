package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.DataStore;

import java.io.IOException;
import java.sql.SQLException;

public class contactAdd {



    @FXML
    private TextField addName;

    @FXML
    private TextField addStreet;

    @FXML
    private TextField addCity;

    @FXML
    private TextField addZip;

    @FXML
    private ComboBox<String> addState;

    @FXML
    private ComboBox<String> addCountry;

    @FXML
    private TextField addTel;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    public void saveAddCust(ActionEvent event) throws Exception{

    }

    public void cancelAddCust(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setStateComboBox(ActionEvent event) {
        String selected = addCountry.getSelectionModel().getSelectedItem();
        System.out.println(selected);
        switch (selected) {
            case "U.S":
                addState.setItems(DAO.Utilities.usStates);
                break;
            case "UK":
                addState.setItems(DAO.Utilities.ukRegions);
                break;
            case "Canada":
                addState.setItems(DAO.Utilities.caProvinces);
                break;
        }


    }



    @FXML
    public void initialize() throws SQLException {
        addCountry.setItems(DAO.Utilities.countries);






    }

}
