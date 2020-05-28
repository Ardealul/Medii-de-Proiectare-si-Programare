package client;

import dto.DTOUtils;
import dto.OficiuDTO;
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
import model.Oficiu;
import services.IServices;
import services.MyException;

import java.io.IOException;

public class LoginController {
    private IServices server = null;
    private Stage stage;
    private MainController mainController;

    public void setServer(IServices server, Stage stage) {
        this.server = server;
        this.stage = stage;
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

    public void handleLoginButton() throws MyException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        try {
            if (server.findOficiuByUserPass(username, password, mainController) != null) {
                System.out.println("Am intrat in try ul din LoginController");
                try {
                    stage.close();

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/mainView.fxml"));

                    AnchorPane layout = loader.load();

                    Stage stage = new Stage();
                    stage.setTitle(username);
                    stage.getIcons().add(new Image("/pics/motor.png"));
                    stage.initModality(Modality.WINDOW_MODAL);

                    MainController mainController = loader.getController();
                    mainController.setServer(server, stage);

                    Scene scene = new Scene(layout, 680, 575);
                    stage.setScene(scene);
                    stage.show();

                    server.setClient(mainController); //
                    Oficiu oficiu = server.findOficiuByUserPass(username, password);
                    OficiuDTO oficiuDTO = DTOUtils.getDTO(oficiu);
                    mainController.setOficiuDTO(oficiuDTO); //
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
        catch (MyException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Utilizator deja conectat!");
            alert.setHeaderText("Incearca din nou!");
            alert.setTitle("Eroare");
            alert.show();
        }
    }
}

