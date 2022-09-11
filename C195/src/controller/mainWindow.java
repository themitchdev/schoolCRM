package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class mainWindow {
    @FXML
    private Button addApptBtn;

    @FXML
    private Button addContactBtn;

    @FXML
    private TableColumn<?, ?> apptContactCol;

    @FXML
    private TableColumn<?, ?> apptCustdCol;

    @FXML
    private TableColumn<?, ?> apptDescriptionCol;

    @FXML
    private TableColumn<?, ?> apptEndTimeCol;

    @FXML
    private TableColumn<?, ?> apptIdCol;

    @FXML
    private TableColumn<?, ?> apptLocationCol;

    @FXML
    private TableColumn<?, ?> apptStartTimeCol;

    @FXML
    private TableView<?> apptTable;

    @FXML
    private TableColumn<?, ?> apptTitleCol;

    @FXML
    private TableColumn<?, ?> apptTypeCol;

    @FXML
    private TableColumn<?, ?> apptUsertIdCol;

    @FXML
    private TableColumn<?, ?> customerCityCol;

    @FXML
    private TableColumn<?, ?> customerIdCol;

    @FXML
    private TableColumn<?, ?> customerNameCol;

    @FXML
    private TableColumn<?, ?> customerStateCol;

    @FXML
    private TableColumn<?, ?> customerStreetCol;

    @FXML
    private TableView<?> customerTable;

    @FXML
    private TableColumn<?, ?> customerTelCol;

    @FXML
    private TableColumn<?, ?> customerZipCol;

    @FXML
    private Button deleteApptBtn;

    @FXML
    private Button deleteContactBtn;

    @FXML
    private TableColumn<?, ?> reportApptIdCol;

    @FXML
    private TableColumn<?, ?> reportCustIdCol;

    @FXML
    private TableColumn<?, ?> reportDescripCol;

    @FXML
    private TableColumn<?, ?> reportEndTimeCol;

    @FXML
    private TableColumn<?, ?> reportStartTimeCol;

    @FXML
    private TableColumn<?, ?> reportTitleCol;

    @FXML
    private TableColumn<?, ?> reportTypeCol;

    @FXML
    private TableView<?> scheduleTable;

    @FXML
    private Button updateApptBtn;

    @FXML
    private Button updateContactBtn;

    @FXML
    public void addContact(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/contactAdd.fxml"));

        Stage stage = new Stage();//(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());

        stage.setScene(new Scene(root, stage.getHeight(), stage.getWidth()));
        stage.setTitle("Add Contact");
        //stage.setScene(scene);
        stage.show();


    }

    @FXML
    public void updateContact(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/contactUpdate.fxml"));

        Stage stage = new Stage();//(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());

        stage.setScene(new Scene(root, stage.getHeight(), stage.getWidth()));
        stage.setTitle("Update Contact");
        //stage.setScene(scene);
        stage.show();


    }

    @FXML
    void addAppt(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/appointmentAdd.fxml"));

        Stage stage = new Stage();//(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());

        stage.setScene(new Scene(root, stage.getHeight(), stage.getWidth()));
        stage.setTitle("Add Appointment");
        //stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteAppt(ActionEvent event) {

    }

    @FXML
    void deleteContact(ActionEvent event) {

    }

    @FXML
    void updateAppt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/appointmentUpdate.fxml"));

        Stage stage = new Stage();//(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());

        stage.setScene(new Scene(root, stage.getHeight(), stage.getWidth()));
        stage.setTitle("Update Appointment");
        //stage.setScene(scene);
        stage.show();
    }


}
