package model;

import Utilities.JDBC;
import Utilities.Misc;
import Utilities.Time;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalTime;
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

        Utilities.JDBC.insert1stLvlDivIntoList(Misc.usStates, "1");
        Utilities.JDBC.insert1stLvlDivIntoList(Misc.ukRegions, "2");
        Utilities.JDBC.insert1stLvlDivIntoList(Misc.caProvinces, "3");

        Misc.buildDivisonIdHash();

        Integer time = 1;
        while(time < 13){
            Time.hours.add(String.format("%02d", time));
            time++;
        }


        launch(args);

        JDBC.closeConnection();


    }
}
