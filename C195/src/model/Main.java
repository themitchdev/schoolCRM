package model;

import Utilities.JDBC;
import Utilities.Login;
import Utilities.Misc;
import Utilities.MyTime;
import javafx.application.Application;
import javafx.collections.ObservableList;
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
        Parent root = FXMLLoader.load(getClass().getResource("../view/loginForm.fxml"));
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
        Utilities.JDBC.insert1stLvlDivIntoList(DataStore.usStates, "1");
        Utilities.JDBC.insert1stLvlDivIntoList(DataStore.ukRegions, "2");
        Utilities.JDBC.insert1stLvlDivIntoList(DataStore.caProvinces, "3");
        DataStore.buildDivisonIdHash();

        Integer time = 8;
        while(time < 22){
            MyTime.hours.add(String.format("%02d", time));
            time++;
        }



        launch(args);

        JDBC.closeConnection();


    }
}
