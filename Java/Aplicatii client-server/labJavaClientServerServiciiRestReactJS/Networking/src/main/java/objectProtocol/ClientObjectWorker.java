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

public class ClientObjectWorker implements Runnable, IObserver {
    private IServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ClientObjectWorker(IServices server, Socket connection){
        this.server = server;
        this.connection = connection;
        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(connected){
            try{
                Object request = input.readObject();
                Object response = handleRequest((Request) request);
                if(response != null){
                    sendResponse((Response) response);
                }
            } catch (IOException | ClassNotFoundException | MyException e) {
                e.printStackTrace();
            }
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try{
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    public void participantCursaAdded() throws MyException{
        System.out.println("ParticipantCursa added");
        try{
            sendResponse(new NewParticipantCursaAddedResponse());
        }
        catch (IOException e){
            throw new MyException("Sending error: " + e);
        }
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("Sending response..." + response);
        output.writeObject(response);
        output.flush();
    }

    private Response handleRequest(Request request) throws MyException {
        Response response = null;
        if(request instanceof LoginRequest){
            System.out.println("Login request...");
            LoginRequest loginRequest = (LoginRequest) request;
            OficiuDTO oficiuDTO = loginRequest.getOficiuDTO();
            Oficiu oficiu = DTOUtils.getfromDTO(oficiuDTO);
            try{
                server.findOficiuByUserPass(oficiu.getUsername(), oficiu.getPassword(), this);
                return new OkResponse();
            } catch (MyException e) {
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof GetOficiuByUserPassRequest){
            System.out.println("GetOficiuByUserPass request");
            GetOficiuByUserPassRequest req = (GetOficiuByUserPassRequest) request;
            String username = req.getUsername();
            String password = req.getPassword();
            Oficiu oficiu = server.findOficiuByUserPass(username, password);
            OficiuDTO oficiuDTO = DTOUtils.getDTO(oficiu);
            return new GetOficiuByUserPassResponse(oficiuDTO);
        }

        if(request instanceof LogoutRequest){
            System.out.println("Logout request");
            LogoutRequest logoutRequest = (LogoutRequest) request;
            OficiuDTO oficiuDTO = logoutRequest.getOficiuDTO();
            Oficiu oficiu = DTOUtils.getfromDTO(oficiuDTO);
            System.out.println("Oficiul in ClientWorker este: " + oficiu);
            try {
                server.logout(oficiu, this);
                connected = false;
                return new OkResponse();
            } catch (MyException e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof GetAllParticipantsRequest){
            System.out.println("GetAllParticipants request");
            try{
                Participant[] participants = server.findAllParticipant();
                return new GetAllParticipantsResponse(participants);
            } catch (MyException e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof GetAllCursaRequest){
            System.out.println("GetAllCursa request");
            try{
                Cursa[] curse = server.findAllCursa();
                CursaDTO[] curseDTO = DTOUtils.getDTO(curse);
                return new GetAllCursaResponse(curseDTO);
            } catch (MyException e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof  GetAllParticipantsByTeamRequest){
            System.out.println("GetAllParticipantsByTeam request");
            GetAllParticipantsByTeamRequest req = (GetAllParticipantsByTeamRequest) request;
            String echipa = req.getEchipa();
            try{
                Participant[] participants = server.findAllParticipant(echipa);
                return new GetAllParticipantsResponse(participants);
            } catch (MyException e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof GetEchipeRequest){
            System.out.println("GetEchipe request");
            try{
                String echipe = server.findAllEchipe();
                return new GetEchipeResponse(echipe);
            } catch (MyException e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof GetParticipantRequest){
            System.out.println("GetParticipant request");
            GetParticipantRequest req = (GetParticipantRequest) request;
            String nume = req.getNume();
            String echipa = req.getEchipa();
            Integer capMotor = req.getCapMotor();
            try{
                Participant participant = server.findParticipant(nume, echipa, capMotor);
                ParticipantDTO participantDTO = DTOUtils.getDTO(participant);
                return new GetParticipantResponse(participantDTO);
            } catch (MyException e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof GetCursaRequest){
            System.out.println("GetCursa request");
            GetCursaRequest req = (GetCursaRequest) request;
            String idCursa = req.getIdCursa();
            try{
                Cursa cursa = server.findCursa(idCursa);
                return new GetCursaResponse(cursa);
            } catch (MyException e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof AddParticipantCursaRequest){
            System.out.println("AddParticipantCursa request");
            AddParticipantCursaRequest req = (AddParticipantCursaRequest) request;
            ParticipantCursaDTO participantCursaDTO = req.getParticipantCursaDTO();
            System.out.println("Dto: ");
            System.out.println(participantCursaDTO);
            ParticipantCursa participantCursa = DTOUtils.getFromDTO(participantCursaDTO);
            try{
                System.out.println("Participantul Cursa de salvat este:");
                System.out.println(participantCursa);
                server.saveParticipantCursa(participantCursa);
                return new OkResponse();
            } catch (MyException e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof UpdateCursaRequest){
            System.out.println("UpdateCursa request");
            UpdateCursaRequest req = (UpdateCursaRequest) request;
            String idCursa = req.getIdCursa();
            CursaDTO cursaDTO = req.getCursaDTO();
            Cursa cursa = DTOUtils.getfromDTO(cursaDTO);
            try{
                server.updateCursa(idCursa, cursa);
                return new OkResponse();
            } catch (MyException e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        return response;
    }

}
