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

public class ProtoWorker implements Runnable, IObserver {
    private IServices server;
    private Socket connection;

    //private ObjectInputStream input;
    //private ObjectOutputStream output;
    private InputStream input;
    private OutputStream output;
    private volatile boolean connected;

    public ProtoWorker(IServices server, Socket connection){
        this.server = server;
        this.connection = connection;
        try{
            output = connection.getOutputStream(); //new ObjectOutputStream(connection.getOutputStream());
            input = connection.getInputStream(); //new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(connected){
            try{
                //Object request = input.readObject();
                Protocol.Request request = Protocol.Request.parseDelimitedFrom(input);
                Protocol.Response response = handleRequest(request);
                if(response != null){
                    sendResponse(response);
                }
            } catch (IOException | MyException e) {
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
            sendResponse(ProtoUtils.createNewParticipantCursaAddedResponse());
        }
        catch (IOException e){
            throw new MyException("Sending error: " + e);
        }
    }

    private void sendResponse(Protocol.Response response) throws IOException{
        System.out.println("Sending response..." + response);
        //output.writeObject(response);
        response.writeDelimitedTo(output);
        output.flush();
    }

    private Protocol.Response handleRequest(Protocol.Request request) throws MyException {
        Protocol.Response response = null;
        switch (request.getType()){
            case Login:{
                System.out.println("Login request...");
                Oficiu oficiu = ProtoUtils.getOficiu(request);
                try{
                    server.findOficiuByUserPass(oficiu.getUsername(), oficiu.getPassword(), this);
                    return ProtoUtils.createOkReponse();
                } catch (MyException e) {
                    connected = false;
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetOficiuByUserPass:{
                System.out.println("GetOficiuByUserPass request");
                String username = ProtoUtils.getUsername(request);
                String password = ProtoUtils.getPassword(request);
                Oficiu oficiu = server.findOficiuByUserPass(username, password);
                return ProtoUtils.createGetOficiuByUserPassResponse(oficiu);
            }
            case Logout:{
                System.out.println("Logout request");
                Oficiu oficiu = ProtoUtils.getOficiu(request);
                System.out.println("Oficiul in ClientWorker este: " + oficiu);
                try{
                    server.logout(oficiu, this);
                    connected = false;
                    return ProtoUtils.createOkReponse();
                } catch (MyException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetAllParticipants:{
                System.out.println("GetAllParticipants request");
                try{
                    Participant[] participants = server.findAllParticipant();
                    return ProtoUtils.createGetAllParticipantsResponse(participants);
                } catch (MyException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetAllCursa:{
                System.out.println("GetAllCursa request");
                try{
                    Cursa[] curse = server.findAllCursa();
                    return ProtoUtils.createGetAllCursaResponse(curse);
                } catch (MyException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetAllParticipantsByTeam:{
                System.out.println("GetAllParticipantsByTeam request");
                String echipa = ProtoUtils.getEchipa(request);
                try{
                    Participant[] participants = server.findAllParticipant(echipa);
                    return ProtoUtils.createGetAllParticipantsResponse(participants);
                } catch (MyException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetEchipe:{
                System.out.println("GetEchipe request");
                try{
                    String echipe = server.findAllEchipe();
                    return ProtoUtils.createGetEchipeResponse(echipe);
                } catch (MyException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetParticipant:{
                System.out.println("GetParticipant request");
                String nume = ProtoUtils.getNume(request);
                String echipa = ProtoUtils.getEchipa(request);
                Integer capMotor = ProtoUtils.getCapMotor(request);
                try{
                    Participant participant = server.findParticipant(nume, echipa, capMotor);
                    return ProtoUtils.createGetParticipantResponse(participant);
                } catch (MyException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetCursa:{
                System.out.println("GetCursa request");
                String idCursa = ProtoUtils.getIdCursa(request);
                try{
                    Cursa cursa = server.findCursa(idCursa);
                    return ProtoUtils.createGetCursaResponse(cursa);
                } catch (MyException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case AddParticipantCursa:{
                System.out.println("AddParticipantCursa request");
                //ParticipantCursaDTO participantCursaDTO = req.getParticipantCursaDTO();
                //System.out.println("Dto: ");
                //System.out.println(participantCursaDTO);
                //ParticipantCursa participantCursa = DTOUtils.getFromDTO(participantCursaDTO);
                ParticipantCursa participantCursa = ProtoUtils.getParticipantCursa(request);
                try{
                    System.out.println("Participantul Cursa de salvat este:");
                    System.out.println(participantCursa);
                    server.saveParticipantCursa(participantCursa);
                    return ProtoUtils.createOkReponse();
                } catch (MyException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case UpdateCursa:{
                System.out.println("UpdateCursa request");
                String idCursa = ProtoUtils.getIdCursa(request);
                Cursa cursa = ProtoUtils.getCursa(request);
                try{
                    server.updateCursa(idCursa, cursa);
                    return ProtoUtils.createOkReponse();
                } catch (MyException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
        }
        return response;
    }
}
