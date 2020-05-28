package ProtocolBuffers;

import dto.CursaDTO;
import dto.DTOUtils;
import dto.OficiuDTO;
import dto.ParticipantDTO;
import model.Cursa;
import model.Oficiu;
import model.Participant;
import model.ParticipantCursa;
import objectProtocol.*;
import services.IObserver;
import services.IServices;
import services.MyException;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoProxy implements IServices {

    private String host;
    private int port;

    private IObserver client;

    //private ObjectOutputStream output;
    //private ObjectInputStream input;
    private OutputStream output;
    private InputStream input;
    private Socket connection;

    private BlockingQueue<Protocol.Response> responseBlockingQueue;
    private volatile boolean finished;

    public ProtoProxy(String host, int port){
        this.host = host;
        this.port = port;
        responseBlockingQueue = new LinkedBlockingQueue<Protocol.Response>();
    }

    public void setClient(IObserver client){
        this.client = client;
        System.out.println("Clientul meu adevarat este: " + client);
    }

    @Override
    public Oficiu findOficiuByUserPass(String username, String password, IObserver client) throws MyException {
        System.out.println("Sunt in findOficiuByUserPass in Proxy");
        initializeConnection();
        sendRequest(ProtoUtils.createGetOficiuByUserPassRequest(username, password));
        Protocol.Response response = readResponse();
        if(response.getType() == Protocol.Response.Type.Error){
            throw new MyException(ProtoUtils.getEroare(response));
        }
        Oficiu oficiu = ProtoUtils.getOficiu(response);
        sendRequest(ProtoUtils.createLoginRequest(oficiu));
        Protocol.Response responseLogin = readResponse();
        if(responseLogin.getType() == Protocol.Response.Type.Ok){
            return oficiu;
        }
        if(responseLogin.getType() == Protocol.Response.Type.Error){
            closeConnection();
            throw new MyException(ProtoUtils.getEroare(responseLogin));
        }
        return null;
    }

    @Override
    public Oficiu findOficiuByUserPass(String username, String password) throws MyException {
        sendRequest(ProtoUtils.createGetOficiuByUserPassRequest(username, password));
        Protocol.Response response = readResponse();
        if(response.getType() == Protocol.Response.Type.Error){
            throw new MyException(ProtoUtils.getEroare(response));
        }
        return ProtoUtils.getOficiu(response);
    }

    @Override
    public Cursa[] findAllCursa() throws MyException {
        sendRequest(ProtoUtils.createGetAllCursaRequest());
        Protocol.Response response = readResponse();
        if(response.getType() == Protocol.Response.Type.Error){
            throw new MyException(ProtoUtils.getEroare(response));
        }
        return ProtoUtils.getCurse(response);
    }

    @Override
    public Participant[] findAllParticipant() throws MyException {
        sendRequest(ProtoUtils.createGetAllParticipantsRequest());
        Protocol.Response response = readResponse();
        if(response.getType() == Protocol.Response.Type.Error){
            throw new MyException(ProtoUtils.getEroare(response));
        }
        return ProtoUtils.getParticipants(response);
    }

    @Override
    public Participant[] findAllParticipant(String echipa) throws MyException {
        sendRequest(ProtoUtils.createGetAllParticipantsByTeamRequest(echipa));
        Protocol.Response response = readResponse();
        if(response.getType() == Protocol.Response.Type.Error){
            throw new MyException(ProtoUtils.getEroare(response));
        }
        return ProtoUtils.getParticipants(response);
    }

    @Override
    public String findAllEchipe() throws MyException{
        sendRequest(ProtoUtils.createGetEchipeRequest());
        Protocol.Response response = readResponse();
        if(response.getType() == Protocol.Response.Type.Error){
            throw new MyException(ProtoUtils.getEroare(response));
        }
        return ProtoUtils.getEchipe(response);
    }

    @Override
    public Participant findParticipant(String nume, String echipa, Integer capMotor) throws MyException {
        sendRequest(ProtoUtils.createGetParticipantRequest(nume, echipa, capMotor));
        Protocol.Response response = readResponse();
        if(response.getType() == Protocol.Response.Type.Error){
            throw new MyException(ProtoUtils.getEroare(response));
        }
        return ProtoUtils.getParticipant(response);
    }

    @Override
    public Cursa findCursa(String idCursa) throws MyException {
        sendRequest(ProtoUtils.createGetCursaRequest(idCursa));
        Protocol.Response response = readResponse();
        if(response.getType() == Protocol.Response.Type.Error){
            throw new MyException(ProtoUtils.getEroare(response));
        }
        return ProtoUtils.getCursa(response);
    }

    @Override
    public void saveParticipantCursa(ParticipantCursa participantCursa) throws MyException {
        //ParticipantCursaDTO participantCursaDTO = DTOUtils.getDTO(participantCursa);
        sendRequest(ProtoUtils.createAddParticipantCursaRequest(participantCursa));
        Protocol.Response response = readResponse();
        if(response.getType() == Protocol.Response.Type.Error){
            throw new MyException(ProtoUtils.getEroare(response));
        }
    }

    @Override
    public void updateCursa(String idCursa, Cursa cursa) throws MyException {
        sendRequest(ProtoUtils.createUpdateCursa(idCursa, cursa));
        Protocol.Response response = readResponse();
        if(response.getType() == Protocol.Response.Type.Error){
            throw new MyException(ProtoUtils.getEroare(response));
        }
    }

    @Override
    public void logout(Oficiu oficiu, IObserver client) throws MyException {
        sendRequest(ProtoUtils.createLogoutRequest(oficiu));
        Protocol.Response response = readResponse();
        closeConnection();
        if(response.getType() == Protocol.Response.Type.Error){
            throw new MyException(ProtoUtils.getEroare(response));
        }
    }

    private void handleUpdate(Protocol.Response updateResponse) throws MyException {
        switch (updateResponse.getType()){
            case NewParticipantCursaAdded:{
                System.out.println("New ParticipantCursa added!");
                System.out.println("My client is: " + client);
                client.participantCursaAdded(); //
                break;
            }
        }
    }

    private void initializeConnection() {
        try{
            connection = new Socket(host, port);
            //output = new ObjectOutputStream(connection.getOutputStream());
            //output.flush();
            //input = new ObjectInputStream(connection.getInputStream());
            output = connection.getOutputStream();
            input = connection.getInputStream();
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection(){
        finished = true;
        try{
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void sendRequest(Protocol.Request request) throws MyException{
        try{
            //output.writeObject(request);
            request.writeDelimitedTo(output);
            output.flush();
        } catch (IOException e) {
            throw new MyException("Error sending object " + e);
        }
    }

    private Protocol.Response readResponse(){
        Protocol.Response response = null;
        try{
            response = responseBlockingQueue.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private class ReaderThread implements Runnable{
        public void run(){
            while(!finished){
                try{
                    //Object response = input.readObject();
                    Protocol.Response response = Protocol.Response.parseDelimitedFrom(input);
                    System.out.println("Response received: " + response);
                    if(isUpdateResponse(response.getType())){
                        handleUpdate(response);
                    }
                    else {
                        try {
                            responseBlockingQueue.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (MyException e) {
                    e.printStackTrace();
                }
                catch (IOException e){
                    System.out.println("");
                }
            }
        }
    }

    private boolean isUpdateResponse(Protocol.Response.Type type){
        switch (type){
            case NewParticipantCursaAdded:
                return true;
        }
        return false;
    }
}
