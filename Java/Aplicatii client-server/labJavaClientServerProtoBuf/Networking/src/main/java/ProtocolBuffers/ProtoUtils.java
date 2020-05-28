package ProtocolBuffers;

import javafx.util.Pair;
import model.Cursa;
import model.Oficiu;
import model.Participant;
import model.ParticipantCursa;

public class ProtoUtils {


    //REQUESTS
    public static Protocol.Request createLoginRequest(Oficiu oficiu){
        Protocol.OficiuDTO oficiuDTO = Protocol.OficiuDTO.newBuilder()
                .setIdOficiu(oficiu.getId())
                .setUsername(oficiu.getUsername())
                .setPassword(oficiu.getPassword())
                .build();
        Protocol.Request request = Protocol.Request.newBuilder()
                .setType(Protocol.Request.Type.Login)
                .setOficiuDTO(oficiuDTO)
                .build();
        return request;
    }

    public static Protocol.Request createGetOficiuByUserPassRequest(String username, String password){
        Protocol.Request request = Protocol.Request.newBuilder()
                .setType(Protocol.Request.Type.GetOficiuByUserPass)
                .setUsername(username)
                .setPassword(password)
                .build();
        return request;
    }

    public static Protocol.Request createLogoutRequest(Oficiu oficiu){
        Protocol.OficiuDTO oficiuDTO = Protocol.OficiuDTO.newBuilder()
                .setIdOficiu(oficiu.getId())
                .setUsername(oficiu.getUsername())
                .setPassword(oficiu.getPassword())
                .build();
        Protocol.Request request = Protocol.Request.newBuilder()
                .setType(Protocol.Request.Type.Logout)
                .setOficiuDTO(oficiuDTO)
                .build();
        return request;
    }

    public static Protocol.Request createGetAllParticipantsRequest(){
        Protocol.Request request = Protocol.Request.newBuilder()
                .setType(Protocol.Request.Type.GetAllParticipants)
                .build();
        return request;
    }

    public static Protocol.Request createGetAllCursaRequest(){
        Protocol.Request request = Protocol.Request.newBuilder()
                .setType(Protocol.Request.Type.GetAllCursa)
                .build();
        return request;
    }

    public static Protocol.Request createGetAllParticipantsByTeamRequest(String echipa){
        Protocol.Request request = Protocol.Request.newBuilder()
                .setType(Protocol.Request.Type.GetAllParticipantsByTeam)
                .setEchipa(echipa)
                .build();
        return request;
    }

    public static Protocol.Request createGetEchipeRequest(){
        Protocol.Request request = Protocol.Request.newBuilder()
                .setType(Protocol.Request.Type.GetEchipe)
                .build();
        return request;
    }

    public static Protocol.Request createGetParticipantRequest(String nume, String echipa, Integer capMotor){
        Protocol.Request request = Protocol.Request.newBuilder()
                .setType(Protocol.Request.Type.GetParticipant)
                .setNume(nume)
                .setEchipa(echipa)
                .setCapMotor(capMotor)
                .build();
        return request;
    }

    public static Protocol.Request createGetCursaRequest(String idCursa){
        Protocol.Request request = Protocol.Request.newBuilder()
                .setType(Protocol.Request.Type.GetCursa)
                .setIdCursa(idCursa)
                .build();
        return request;
    }

    public static Protocol.Request createAddParticipantCursaRequest(ParticipantCursa participantCursa){
        Protocol.ParticipantCursa participantCursaDTO = Protocol.ParticipantCursa.newBuilder()
                .setIdParticipant(participantCursa.getIdParticipant())
                .setIdCursa(participantCursa.getIdCursa())
                .build();
        Protocol.Request request = Protocol.Request.newBuilder()
                .setType(Protocol.Request.Type.AddParticipantCursa)
                .setParticipantCursa(participantCursaDTO)
                .build();
        return request;
    }

    public static Protocol.Request createUpdateCursa(String idCursa, Cursa cursa){
        Protocol.CursaDTO cursaDTO = Protocol.CursaDTO.newBuilder()
                .setIdCursa(cursa.getId())
                .setNrPersoane(cursa.getNrPersoane())
                .setCapMotor(cursa.getCapacitateMotor())
                .build();
        Protocol.Request request = Protocol.Request.newBuilder()
                .setType(Protocol.Request.Type.UpdateCursa)
                .setIdCursa(idCursa)
                .setCursaDTO(cursaDTO)
                .build();
        return request;
    }


    //RESPONSES
    public static Protocol.Response createOkReponse(){
        Protocol.Response response = Protocol.Response.newBuilder()
                .setType(Protocol.Response.Type.Ok)
                .build();
        return response;
    }

    public static Protocol.Response createErrorResponse(String eroare){
        Protocol.Response response = Protocol.Response.newBuilder()
                .setType(Protocol.Response.Type.Error)
                .setEroare(eroare)
                .build();
        return response;
    }

    public static Protocol.Response createGetOficiuByUserPassResponse(Oficiu oficiu){
        Protocol.OficiuDTO oficiuDTO = Protocol.OficiuDTO.newBuilder()
                .setIdOficiu(oficiu.getId())
                .setUsername(oficiu.getUsername())
                .setPassword(oficiu.getPassword())
                .build();
        Protocol.Response response = Protocol.Response.newBuilder()
                .setType(Protocol.Response.Type.GetOficiuByUserPass)
                .setOficiuDTO(oficiuDTO)
                .build();
        return response;
    }

    public static Protocol.Response createGetAllCursaResponse(Cursa[] curse){
        Protocol.Response.Builder response = Protocol.Response.newBuilder()
                .setType(Protocol.Response.Type.GetAllCursa);

        for (Cursa cursa : curse){
            Protocol.CursaDTO cursaDTO = Protocol.CursaDTO.newBuilder()
                    .setIdCursa(cursa.getId())
                    .setNrPersoane(cursa.getNrPersoane())
                    .setCapMotor(cursa.getCapacitateMotor())
                    .build();
            response.addCursaDTO(cursaDTO);
        }
        return response.build();
    }

    public static Protocol.Response createGetAllParticipantsResponse(Participant[] participants){
        Protocol.Response.Builder response = Protocol.Response.newBuilder()
                .setType(Protocol.Response.Type.GetAllParticipants);
        for (Participant participant : participants){
            Protocol.Participant participantDTO = Protocol.Participant.newBuilder()
                    .setNume(participant.getNume())
                    .setEchipa(participant.getEchipa())
                    .setCapMotor(participant.getCapacitateMotor())
                    .build();
            response.addParticipant(participantDTO);
        }
        return response.build();
    }

    public static Protocol.Response createGetEchipeResponse(String echipa){
        Protocol.Response response = Protocol.Response.newBuilder()
                .setType(Protocol.Response.Type.GetEchipe)
                .setEchipe(echipa)
                .build();
        return response;
    }

    public static Protocol.Response createGetParticipantResponse(Participant participant){
        Protocol.ParticipantDTO participantDTO = Protocol.ParticipantDTO.newBuilder()
                .setIdParticipant(participant.getId())
                .setNume(participant.getNume())
                .setEchipa(participant.getEchipa())
                .setCapMotor(participant.getCapacitateMotor())
                .build();
        Protocol.Response response = Protocol.Response.newBuilder()
                .setType(Protocol.Response.Type.GetParticipant)
                .setParticipantDTO(participantDTO)
                .build();
        return response;
    }

    public static Protocol.Response createGetCursaResponse(Cursa cursa){
        Protocol.Cursa cursaDTO = Protocol.Cursa.newBuilder()
                .setNrPersoane(cursa.getNrPersoane())
                .setCapMotor(cursa.getCapacitateMotor())
                .build();
        Protocol.Response response = Protocol.Response.newBuilder()
                .setType(Protocol.Response.Type.GetCursa)
                .setCursa(cursaDTO)
                .build();
        return response;
    }

    public static Protocol.Response createNewParticipantCursaAddedResponse(){
        Protocol.Response response = Protocol.Response.newBuilder()
                .setType(Protocol.Response.Type.NewParticipantCursaAdded)
                .build();
        return response;
    }


    //GET from REQUESTS
    public static Oficiu getOficiu(Protocol.Request request){
        Oficiu oficiu = new Oficiu("", "");
        oficiu.setId(request.getOficiuDTO().getIdOficiu());
        oficiu.setUsername(request.getOficiuDTO().getUsername());
        oficiu.setPassword(request.getOficiuDTO().getPassword());
        return oficiu;
    }

    public static String getUsername(Protocol.Request request){
        return request.getUsername();
    }

    public static String getPassword(Protocol.Request request){
        return request.getPassword();
    }

    public static String getEchipa(Protocol.Request request){
        return request.getEchipa();
    }

    public static String getNume(Protocol.Request request){
        return request.getNume();
    }

    public static Integer getCapMotor(Protocol.Request request){
        return request.getCapMotor();
    }

    public static String getIdCursa(Protocol.Request request){
        return request.getIdCursa();
    }

    public static ParticipantCursa getParticipantCursa(Protocol.Request request){
        ParticipantCursa participantCursa = new ParticipantCursa("", "");
        Pair<String, String> id = new Pair<>(request.getParticipantCursa().getIdParticipant(), request.getParticipantCursa().getIdCursa());
        participantCursa.setId(id);
        participantCursa.setIdParticipant(request.getParticipantCursa().getIdParticipant());
        participantCursa.setIdCursa(request.getParticipantCursa().getIdCursa());
        return participantCursa;
    }

    public static Cursa getCursa(Protocol.Request request){
        Cursa cursa = new Cursa(0, 0);
        cursa.setId(request.getCursaDTO().getIdCursa());
        cursa.setNrPersoane(request.getCursaDTO().getNrPersoane());
        cursa.setCapacitateMotor(request.getCursaDTO().getCapMotor());
        return cursa;
    }


    //GET from RESPONSES
    public static String getEroare(Protocol.Response response){
        return response.getEroare();
    }

    public static Oficiu getOficiu(Protocol.Response response){
        Oficiu oficiu = new Oficiu("", "");
        oficiu.setId(response.getOficiuDTO().getIdOficiu());
        oficiu.setUsername(response.getOficiuDTO().getUsername());
        oficiu.setPassword(response.getOficiuDTO().getPassword());
        return oficiu;
    }

    public static Cursa[] getCurse(Protocol.Response response){
        Cursa[] curse = new Cursa[response.getCursaDTOCount()];
        for(int i = 0; i < response.getCursaDTOCount(); i++){
            Protocol.CursaDTO cursaDTO = response.getCursaDTO(i);
            Cursa cursa = new Cursa(0, 0);
            cursa.setId(cursaDTO.getIdCursa());
            cursa.setNrPersoane(cursaDTO.getNrPersoane());
            cursa.setCapacitateMotor(cursaDTO.getCapMotor());
            curse[i] = cursa;
        }
        return curse;
    }

    public static Participant[] getParticipants(Protocol.Response response){
        Participant[] participants = new Participant[response.getParticipantCount()];
        for(int i = 0; i < response.getParticipantCount(); i++){
            Protocol.Participant participantDTO = response.getParticipant(i);
            Participant participant = new Participant("", "", 0);
            participant.setNume(participantDTO.getNume());
            participant.setEchipa(participantDTO.getEchipa());
            participant.setCapacitateMotor(participantDTO.getCapMotor());
            participants[i] = participant;
        }
        return participants;
    }

    public static String getEchipe(Protocol.Response response){
        return response.getEchipe();
    }

    public static Participant getParticipant(Protocol.Response response){
        Participant participant = new Participant("", "", 0);
        //System.out.println("response ul primit in ProtoUtils este: " + response.getCursa());
        System.out.println("participantulDTO primit in ProtoUtils este: " + response.getParticipantDTO());
        Protocol.ParticipantDTO participantDTO = response.getParticipantDTO();
        participant.setId(participantDTO.getIdParticipant());
        participant.setNume(participantDTO.getNume());
        participant.setEchipa(participantDTO.getEchipa());
        participant.setCapacitateMotor(participantDTO.getCapMotor());
        return participant;
    }

    public static Cursa getCursa(Protocol.Response response){
        Cursa cursa = new Cursa(0, 0);
        System.out.println("cursa primita in ProtoUtils este: " + response.getCursa());
        Protocol.Cursa cursaDTO = response.getCursa();
        cursa.setNrPersoane(cursaDTO.getNrPersoane());
        cursa.setCapacitateMotor(cursaDTO.getCapMotor());
        return cursa;
    }


    //
}
