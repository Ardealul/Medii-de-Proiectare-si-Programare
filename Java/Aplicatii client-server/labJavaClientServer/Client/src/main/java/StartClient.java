import client.LoginController;
import client.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objectProtocol.ServicesObjectProxy;
import services.IServices;

import java.io.IOException;
import java.util.Properties;

public class StartClient extends Application {

    private static int defaultPort = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Client starts!");
        Properties clientProperties = new Properties();
        try{
            clientProperties.load(StartClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set!");
            clientProperties.list(System.out);
        }
        catch (IOException ex){
            System.err.println("Cannot found file client.properties " + ex);
            return;
        }

        String serverIP = clientProperties.getProperty("server.host", defaultServer);
        int serverPort = defaultPort;

        try{
            serverPort = Integer.parseInt(clientProperties.getProperty("server.port"));
        }
        catch (NumberFormatException ex){
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        //proxy

        IServices server = new ServicesObjectProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/loginView.fxml"));

        AnchorPane layout = loader.load();

        LoginController loginController = loader.getController();
        loginController.setServer(server, stage);

        Scene scene = new Scene(layout, 600,400);
        stage.setScene(scene);
        stage.setTitle("Aplicatia momentului");
        stage.getIcons().add(new Image("/pics/login2.png"));
        stage.show();

    }
}
