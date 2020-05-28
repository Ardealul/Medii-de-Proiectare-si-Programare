import repository.CursaRepository;
import repository.OficiuRepository;
import repository.ParticipantCursaRepository;
import repository.ParticipantRepository;
import server.ServiceImpl;
import services.IServices;
import utils.AbstractServer;
import utils.ObjectConcurrentServer;
import utils.ServerException;

import java.io.IOException;
import java.util.Properties;

public class StartServer {
    private static int defaultPort = 55555;
    public static void main(String[] args) {
        Properties serverProperties = new Properties();
        try{
            serverProperties.load(StartServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set!");
            serverProperties.list(System.out);
        } catch (IOException ex) {
            System.out.println("Cannot find file server.properties " + ex);
            return;
        }
        OficiuRepository oficiuRepository = new OficiuRepository(serverProperties);
        ParticipantRepository participantRepository = new ParticipantRepository(serverProperties);
        CursaRepository cursaRepository = new CursaRepository(serverProperties);
        ParticipantCursaRepository participantCursaRepository = new ParticipantCursaRepository(serverProperties);
        IServices serverImpl = new ServiceImpl(oficiuRepository, participantRepository, cursaRepository, participantCursaRepository);

        int serverPort = defaultPort;
        try{
            serverPort = Integer.parseInt(serverProperties.getProperty("server.port"));
        }
        catch (NumberFormatException ex){
            System.err.println("Wrong port number" + ex.getMessage());
            System.err.println("Using default port " + defaultPort);
        }
        System.out.println("Starting server on port: " + serverPort);

        //server
        AbstractServer server = new ObjectConcurrentServer(serverPort, serverImpl);
        try{
            server.start();
        } catch (ServerException ex) {
            System.err.println("Error starting the server!" + ex.getMessage());
        }

    }
}
