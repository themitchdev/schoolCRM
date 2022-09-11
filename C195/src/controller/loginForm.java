package controller;

import DAO.login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class loginForm {
    ResourceBundle rb = ResourceBundle.getBundle("view/myApp", Locale.getDefault());
    @FXML
    private Button loginButton;

    @FXML
    private Label mainmsg;

    @FXML
    private TextField password;

    @FXML
    private Label secondmsg;

    @FXML
    private Label textbox1;

    @FXML
    private Label textbox2;

    @FXML
    private Label userLocationLabel;

    @FXML
    private TextField userName;

    Locale zone = Locale.getDefault();
    @FXML
    public void initialize(){

        userLocationLabel.setText("your connecting from " + zone.getDisplayCountry());
        if(Locale.getDefault().getLanguage().equals("fr")){
            loginButton.setText(rb.getString("button1"));
            mainmsg.setText(rb.getString("mainmsg"));
            secondmsg.setText(rb.getString("secondmsg"));
            textbox1.setText(rb.getString("textbox1"));
            textbox2.setText(rb.getString("textbox2"));
            userLocationLabel.setText(rb.getString("footermsg")+ " " + zone.getDisplayCountry());

        }

    }

    public void invalidUserPassAlert(String string) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(Locale.getDefault().getLanguage().equals("fr")) {
            alert.setTitle(rb.getString("alerttitle"));
        }else{
            alert.setTitle("Login Incorrect");
        }
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }


    public void loginButtonPressed(ActionEvent event) throws IOException, SQLException {
        String uname = userName.getText();
        String pword = password.getText();
        System.out.println(uname + pword);
        if(login.validateUserPassword(uname, pword)) {
            Parent root = FXMLLoader.load(getClass().getResource("../view/mainWindow.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1080, 720);
            stage.setTitle("Main Form");
            stage.setScene(scene);
            stage.show();
        }
        else{
            if(Locale.getDefault().getLanguage().equals("fr")) {
                invalidUserPassAlert(rb.getString("invaliduserdpass"));;
            }else{
                invalidUserPassAlert("Wrong username or password");
            }

            System.out.println(zone);


        }
    }
}



