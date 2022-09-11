package model;

import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ResourceBundle rb = ResourceBundle.getBundle("view/myApp", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("../view/loginForm.fxml"));
        if(Locale.getDefault().getLanguage().equals("fr")){
            primaryStage.setTitle(rb.getString("windowtitle"));
        }else {
            primaryStage.setTitle("Login");
        }
        primaryStage.setScene(new Scene(root, primaryStage.getHeight(), primaryStage.getWidth()));
        primaryStage.show();

    }


    public static void main(String[] args) {
        Locale.setDefault(new Locale("fr", "CA"));

        JDBC.openConnection();

        launch(args);

        JDBC.closeConnection();


    }
}
