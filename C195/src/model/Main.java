package model;

import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
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

        JDBC.openConnection();

        DAO.JDBC.insert1stLvlDivIntoList(DAO.Utilities.usStates, "1");
        DAO.JDBC.insert1stLvlDivIntoList(DAO.Utilities.ukRegions, "2");
        DAO.JDBC.insert1stLvlDivIntoList(DAO.Utilities.caProvinces, "3");
        DAO.Utilities.buildDivisonIdHash();

        launch(args);

        JDBC.closeConnection();


    }
}
