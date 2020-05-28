import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repo.CursaRepository;
import repo.OficiuRepository;
import repo.ParticipantCursaRepository;
import repo.ParticipantRepository;
import service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainApp extends Application {

    private OficiuRepository oficiuRepository;
    private ParticipantRepository participantRepository;
    private CursaRepository cursaRepository;
    private ParticipantCursaRepository participantCursaRepository;
    private Service service;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties properties = new Properties();
        try{
            properties.load(new FileReader("bd.config"));
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        oficiuRepository = new OficiuRepository(properties);
        participantRepository = new ParticipantRepository(properties);
        cursaRepository = new CursaRepository(properties);
        participantCursaRepository = new ParticipantCursaRepository(properties);

        service = new Service(oficiuRepository, participantRepository, cursaRepository, participantCursaRepository);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/loginView.fxml"));

        AnchorPane layout = loader.load();

        LoginController loginController = loader.getController();
        loginController.setService(service);

        Scene scene = new Scene(layout, 600,400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Aplicatia momentului");
        primaryStage.getIcons().add(new Image("/pics/login2.png"));
        primaryStage.show();

    }
}
