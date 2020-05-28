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
import java.rmi.RemoteException;

public class LoginController {
    private IServices server = null;
    private Stage stage;
    private MainController mainController;

    public void setServer(IServices server, Stage stage) {
        System.out.println("Serverul in LoginController este: " + server);
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

    public void handleLoginButton() throws MyException, IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/mainView.fxml"));

            AnchorPane layout = loader.load();

            Stage mainStage = new Stage();
            mainStage.setTitle(username);
            mainStage.getIcons().add(new Image("/pics/motor.png"));
            mainStage.initModality(Modality.WINDOW_MODAL);

            MainController mainController = loader.getController();
            mainController.setServer(server, mainStage);
            Scene scene = new Scene(layout, 680, 575);
            mainStage.setScene(scene);
            if (mainController.login(username, password) != null) {
                mainStage.show();
                stage.hide();
                Oficiu oficiu = server.findOficiuByUserPass(username, password);
                System.out.println("Oficiul din LoginController este: " + oficiu);
                OficiuDTO oficiuDTO = DTOUtils.getDTO(oficiu);
                mainController.setOficiuDTO(oficiuDTO); //
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
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

