package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;

public class LoginController {
    private Service service = null;

    public void setService(Service service) {
        this.service = service;
        setMaxLength();
    }

    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    Button loginButton;

    private void setMaxLength(){
        usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(usernameTextField.getText().length() > 20){
                String s = usernameTextField.getText().substring(0, 20);
                usernameTextField.setText(s);
            }
        });

        passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(passwordTextField.getText().length() > 20){
                String s = passwordTextField.getText().substring(0, 20);
                passwordTextField.setText(s);
            }
        });
    }

    public void handleLoginButton() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (service.findOficiuByUserPass(username, password) != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/mainView.fxml"));

                AnchorPane layout = loader.load();

                Stage stage = new Stage();
                stage.setTitle(username);
                stage.getIcons().add(new Image("/pics/motor.png"));
                stage.initModality(Modality.WINDOW_MODAL);

                MainController mainController = loader.getController();
                mainController.setService(service, stage);

                Scene scene = new Scene(layout, 680, 575);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username sau parola gresita!");
            alert.setHeaderText("Incerca din nou!");
            alert.setTitle("Eroare");
            alert.show();
        }
    }
}

