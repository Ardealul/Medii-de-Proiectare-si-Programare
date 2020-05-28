package dto;

import javafx.util.Pair;
import model.Cursa;
import model.Oficiu;
import model.Participant;
import model.ParticipantCursa;

public class DTOUtils {
    public static CursaDTO getDTO(Cursa cursa){
        String id = cursa.getId();
        Integer nrPersoane = cursa.getNrPersoane();
        Integer capMotor = cursa.getCapacitateMotor();
        return new CursaDTO(id, nrPersoane, capMotor);
    }

    public static Cursa getfromDTO(CursaDTO cursaDTO){
        Integer nrPersoane = cursaDTO.getNrPersoane();
        Integer capMotor = cursaDTO.getCapMotor();
        Cursa cursa = new Cursa(nrPersoane, capMotor);
        cursa.setId(cursaDTO.getId());
        return cursa;
    }

    public static Cursa[] getfromDTO(CursaDTO[] curseDTO){
        Cursa[] curse = new Cursa[curseDTO.length];
        for(int i = 0; i < curseDTO.length; i++){
            curse[i] = getfromDTO(curseDTO[i]);
        }
        return curse;
    }

    public static CursaDTO[] getDTO(Cursa[] curse){
        CursaDTO[] curseDTO = new CursaDTO[curse.length];
        for(int i = 0; i < curse.length; i++){
            curseDTO[i] = getDTO(curse[i]);
        }
        return curseDTO;
    }

    public static ParticipantCursaDTO getDTO(ParticipantCursa participantCursa){
        System.out.println("PARTICIPANT CURSA:" + participantCursa);
        Pair<String, String> id = participantCursa.getId();
        String idParticipant = participantCursa.getIdParticipant();
        String idCursa = participantCursa.getIdCursa();
        return new ParticipantCursaDTO(id, idParticipant, idCursa);
    }

    public static ParticipantCursa getFromDTO(ParticipantCursaDTO participantCursaDTO){
        String idParticipant = participantCursaDTO.getIdParticipant();
        String idCursa = participantCursaDTO.getIdCursa();
        ParticipantCursa participantCursa = new ParticipantCursa(idParticipant, idCursa);
        participantCursa.setId(participantCursaDTO.getId());
        return participantCursa;
    }

    public static ParticipantDTO getDTO(Participant participant){
        String id = participant.getId();
        String nume = participant.getNume();
        String echipa = participant.getEchipa();
        Integer capMotor = participant.getCapacitateMotor();
        return new ParticipantDTO(id, nume, echipa, capMotor);
    }

    public static Participant getfromDTO(ParticipantDTO participantDTO){
        String nume = participantDTO.getNume();
        String echipa = participantDTO.getEchipa();
        Integer capMotor = participantDTO.getCapMotor();
        Participant participant = new Participant(nume, echipa, capMotor);
        participant.setId(participantDTO.getId());
        return participant;
    }

    public static OficiuDTO getDTO(Oficiu oficiu){
        String id = oficiu.getId();
        String username = oficiu.getUsername();
        String password = oficiu.getPassword();
        return new OficiuDTO(id, username, password);
    }

    public static Oficiu getfromDTO(OficiuDTO oficiuDTO){
        String username = oficiuDTO.getUsername();
        String password = oficiuDTO.getPassword();
        Oficiu oficiu = new Oficiu(username, password);
        oficiu.setId(oficiuDTO.getId());
        return oficiu;
    }
}
