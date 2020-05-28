package objectProtocol;

import dto.*;
import model.Cursa;
import model.Oficiu;
import model.Participant;
import model.ParticipantCursa;
import services.IObserver;
import services.IServices;
import services.MyException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesObjectProxy implements IServices {

    private String host;
    private int port;

    private IObserver client;

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket connection;

    private BlockingQueue<Response> responseBlockingQueue;
    private volatile boolean finished;

    public ServicesObjectProxy(String host, int port){
        this.host = host;
        this.port = port;
        responseBlockingQueue = new LinkedBlockingQueue<>();
    }

    public void setClient(IObserver client){
        this.client = client;
        System.out.println("Clientul meu adevarat este: " + client);
    }

    @Override
    public Oficiu findOficiuByUserPass(String username, String password, IObserver client) throws MyException {
        initializeConnection();
        sendRequest(new GetOficiuByUserPassRequest(username, password));
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new MyException(err.getMessage());
        }
        GetOficiuByUserPassResponse resp = (GetOficiuByUserPassResponse) response;
        OficiuDTO oficiuDTO = resp.getOficiuDTO();
        Oficiu oficiu = DTOUtils.getfromDTO(oficiuDTO);
        sendRequest(new LoginRequest(oficiuDTO));
        Response responseLogin = readResponse();

        if(responseLogin instanceof OkResponse){
            return oficiu;
        }
        if(responseLogin instanceof ErrorResponse){
            ErrorResponse error = (ErrorResponse) responseLogin;
            closeConnection();
            throw new MyException(error.getMessage());
        }
        return null;
    }

    @Override
    public Oficiu findOficiuByUserPass(String username, String password) throws MyException {
        sendRequest(new GetOficiuByUserPassRequest(username, password));
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new MyException(err.getMessage());
        }
        GetOficiuByUserPassResponse resp = (GetOficiuByUserPassResponse) response;
        OficiuDTO oficiuDTO = resp.getOficiuDTO();
        return DTOUtils.getfromDTO(oficiuDTO);
    }

    @Override
    public Cursa[] findAllCursa() throws MyException {
        sendRequest(new GetAllCursaRequest());
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new MyException(err.getMessage());
        }
        GetAllCursaResponse resp = (GetAllCursaResponse) response;
        CursaDTO[] cursaDTO = resp.getCurse();
        Cursa[] curse = DTOUtils.getfromDTO(cursaDTO);
        return curse;
    }

    @Override
    public Participant[] findAllParticipant() throws MyException {
        sendRequest(new GetAllParticipantsRequest());
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new MyException(err.getMessage());
        }
        GetAllParticipantsResponse resp = (GetAllParticipantsResponse) response;
        return resp.getParticipants();
    }

    @Override
    public Participant[] findAllParticipant(String echipa) throws MyException {
        sendRequest(new GetAllParticipantsByTeamRequest(echipa));
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new MyException(err.getMessage());
        }
        GetAllParticipantsResponse resp = (GetAllParticipantsResponse) response;
        return resp.getParticipants();
    }

    @Override
    public String findAllEchipe() throws MyException{
        sendRequest(new GetEchipeRequest());
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new MyException(err.getMessage());
        }
        GetEchipeResponse resp = (GetEchipeResponse) response;
        return resp.getEchipe();
    }

    @Override
    public Participant findParticipant(String nume, String echipa, Integer capMotor) throws MyException {
        sendRequest(new GetParticipantRequest(nume, echipa, capMotor));
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new MyException(err.getMessage());
        }
        GetParticipantResponse participantResponse = (GetParticipantResponse) response;
        ParticipantDTO participantDTO = participantResponse.getParticipantDTO();
        Participant participant = DTOUtils.getfromDTO(participantDTO);
        return participant;
    }

    @Override
    public Cursa findCursa(String idCursa) throws MyException {
        sendRequest(new GetCursaRequest(idCursa));
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new MyException(err.getMessage());
        }
        GetCursaResponse resp = (GetCursaResponse) response;
        return resp.getCursa();
    }

    @Override
    public void saveParticipantCursa(ParticipantCursa participantCursa) throws MyException {
        ParticipantCursaDTO participantCursaDTO = DTOUtils.getDTO(participantCursa);
        sendRequest(new AddParticipantCursaRequest(participantCursaDTO));
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new MyException(err.getMessage());
        }
    }

    @Override
    public void updateCursa(String idCursa, Cursa cursa) throws MyException {
        CursaDTO cursaDTO = DTOUtils.getDTO(cursa);
        sendRequest(new UpdateCursaRequest(idCursa, cursaDTO));
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new MyException(err.getMessage());
        }
    }

    @Override
    public void logout(Oficiu oficiu, IObserver client) throws MyException {
        OficiuDTO oficiuDTO = DTOUtils.getDTO(oficiu);
        sendRequest(new LogoutRequest(oficiuDTO));
        Response response = readResponse();
        closeConnection();
        if(response instanceof ErrorResponse){
            ErrorResponse error = (ErrorResponse) response;
            throw new MyException(error.getMessage());
        }
    }

    private void handleUpdate(UpdateResponse updateResponse) throws MyException {
        if(updateResponse instanceof NewParticipantCursaAddedResponse){
            System.out.println("New ParticipantCursa added!");
            System.out.println("My client is: " + client);
            client.participantCursaAdded(); //
        }
    }

    private void initializeConnection() {
        try{
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
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

    private void sendRequest(Request request) throws MyException{
        try{
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new MyException("Error sending object " + e);
        }
    }

    private Response readResponse(){
        Response response = null;
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
                    Object response = input.readObject();
                    System.out.println("Response received: " + response);
                    if(response instanceof UpdateResponse){
                        handleUpdate((UpdateResponse) response);
                    }
                    else {
                        try {
                            responseBlockingQueue.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (ClassNotFoundException | MyException e) {
                    e.printStackTrace();
                }
                catch (IOException e){
                    System.out.println("");
                }
            }
        }
    }
}
