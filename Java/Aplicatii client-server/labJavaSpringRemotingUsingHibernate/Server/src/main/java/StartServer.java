import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.CursaRepository;
import repository.OficiuRepository;
import repository.ParticipantCursaRepository;
import repository.ParticipantRepository;
import server.ServiceImpl;
import services.IServices;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

public class StartServer {
    public static void main(String[] args) {
//        Properties serverProperties = new Properties();
//        try{
//            serverProperties.load(StartServer.class.getResourceAsStream("/server.properties"));
//            System.out.println("Server properties set!");
//            serverProperties.list(System.out);
//        } catch (IOException ex) {
//            System.out.println("Cannot find file server.properties " + ex);
//            return;
//        }
//        OficiuRepository oficiuRepository = new OficiuRepository(serverProperties);
//        ParticipantRepository participantRepository = new ParticipantRepository(serverProperties);
//        CursaRepository cursaRepository = new CursaRepository(serverProperties);
//        ParticipantCursaRepository participantCursaRepository = new ParticipantCursaRepository(serverProperties);
//        IServices serverImpl = new ServiceImpl(oficiuRepository, participantRepository, cursaRepository, participantCursaRepository);
//
//        try{
//            String name = serverProperties.getProperty("serverID", "ClientServer");
//            Registry registry = LocateRegistry.getRegistry();
//            System.out.println("Before binding...");
//            registry.rebind(name, serverImpl);
//            System.out.println("Server bound!");
//        }
//        catch (Exception e){
//            System.out.println("Server exception");
//            e.printStackTrace();
//        }

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
    }
}
