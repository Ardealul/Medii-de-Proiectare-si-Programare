package dto;

import javafx.util.Pair;

import java.io.Serializable;

public class ParticipantCursaDTO implements Serializable {
    private Pair<String, String> id;
    private String idParticipant;
    private String idCursa;

    public ParticipantCursaDTO(Pair<String, String> id, String idParticipant, String idCursa) {
        this.id = id;
        this.idParticipant = idParticipant;
        this.idCursa = idCursa;
    }

    public Pair<String, String> getId() {
        return id;
    }

    public String getIdParticipant() {
        return idParticipant;
    }

    public String getIdCursa() {
        return idCursa;
    }

    @Override
    public String toString() {
        return "ParticipantCursaDTO{" +
                "id=" + id +
                ", idParticipant='" + idParticipant + '\'' +
                ", idCursa='" + idCursa + '\'' +
                '}';
    }
}
