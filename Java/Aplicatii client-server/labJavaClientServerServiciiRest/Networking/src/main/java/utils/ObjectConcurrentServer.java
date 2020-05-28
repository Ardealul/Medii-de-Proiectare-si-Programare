package utils;

import objectProtocol.ClientObjectWorker;
import services.IServices;

import java.net.Socket;

public class ObjectConcurrentServer extends AbstractConcurrentServer {
    private IServices server;

    public ObjectConcurrentServer(int port, IServices server) {
        super(port);
        this.server = server;
        System.out.println("ObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientObjectWorker worker = new ClientObjectWorker(server, client);
        Thread tw = new Thread(worker);
        return tw;
    }
}
