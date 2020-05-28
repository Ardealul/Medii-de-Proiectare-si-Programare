package utils;

import services.IServices;

import java.net.Socket;

public class RpcConcurrentServer extends AbstractConcurrentServer {
    private IServices server;

    public RpcConcurrentServer(int port, IServices server) {
        super(port);
        this.server = server;
        System.out.println("RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        return null;
    }
}
