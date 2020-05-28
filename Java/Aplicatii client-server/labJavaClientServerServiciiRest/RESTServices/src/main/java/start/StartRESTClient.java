package start;

import client.CursaClient;
import model.Cursa;
import org.springframework.web.client.RestClientException;
import services.ServiceException;

public class StartRESTClient {
    private static final CursaClient cursaClient = new CursaClient();

    public static void main(String[] args) {
        Cursa cursa = new Cursa(0, 700);
        cursa.setId("5");

        try{
            //create
            show(() -> System.out.println("Cursa adaugata: " + cursaClient.create(cursa)));

            //update
            cursa.setCapacitateMotor(699);
            show(() -> cursaClient.update(cursa, cursa.getId()));

            //findOne
            show(() -> System.out.println("Cursa modificata: " + cursaClient.getById(cursa.getId())));

            //findAll before delete
            show(() -> {
                Cursa[] curse = cursaClient.getAll();
                System.out.println("Cursele existente inainte de delete: ");
                for (Cursa c : curse) {
                    System.out.println(c);
                }
            });

            //delete
            show(() -> cursaClient.delete(cursa.getId()));

            //findAll after delete
            show(() -> {
                Cursa[] curse = cursaClient.getAll();
                System.out.println("Cursele existente dupa delete: ");
                for (Cursa c : curse) {
                    System.out.println(c);
                }
            });
        }
        catch (RestClientException ex){
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    private static void show(Runnable task){
        try{
            task.run();
        }
        catch (ServiceException ex){
            System.out.println("Service exception: " + ex.getMessage());
        }
    }
}
