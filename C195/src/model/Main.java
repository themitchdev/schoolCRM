package model;

import DAO.JDBC;
import controller.contactAdd;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/mainWindow.fxml"));
        if(Locale.getDefault().getLanguage().equals("fr")){
            ResourceBundle rb = ResourceBundle.getBundle("view/myApp", Locale.getDefault());
            primaryStage.setTitle(rb.getString("windowtitle"));
        }else {
            primaryStage.setTitle("Login");
        }
        primaryStage.setScene(new Scene(root, primaryStage.getHeight(), primaryStage.getWidth()));
        primaryStage.show();

    }


    public static void main(String[] args) throws SQLException {
        //Locale.setDefault(new Locale("fr", "IT"));
        JDBC.openConnection();

        DAO.JDBC.insertIntoObservableList(DAO.Utilities.usStates, "1");
        DAO.JDBC.insertIntoObservableList(DAO.Utilities.ukRegions, "2");
        DAO.JDBC.insertIntoObservableList(DAO.Utilities.caProvinces, "3");



//        JDBC.apptGetContactName(1);
//        JDBC.customerGetState(1);
//        JDBC.customerGetCountry(1);
        launch(args);

        JDBC.closeConnection();


    }
}
