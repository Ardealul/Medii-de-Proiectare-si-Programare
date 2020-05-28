package utils;

import ProtocolBuffers.ProtoWorker;
import services.IServices;

import java.net.Socket;

public class ProtobuffConcurrentServer extends AbstractConcurrentServer {
    private IServices server;

    public ProtobuffConcurrentServer(int port, IServices server) {
        super(port);
        this.server = server;
        System.out.println("ProtobuffConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ProtoWorker worker = new ProtoWorker(server, client);
        Thread tw = new Thread(worker);
        return tw;
    }
}
