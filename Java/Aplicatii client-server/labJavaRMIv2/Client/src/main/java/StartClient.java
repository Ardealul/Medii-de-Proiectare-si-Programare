import client.LoginController;
import client.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.IServices;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class StartClient extends Application {
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
        String name = clientProperties.getProperty("serverID", "ClientServer");

        try{
            Registry registry = LocateRegistry.getRegistry(serverIP);
            IServices server = (IServices) registry.lookup(name);
            System.out.println("Obtained a reference to remote server" + server);

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
        catch (Exception e){
            System.out.println("Client exception");
            e.printStackTrace();
        }
    }
}
