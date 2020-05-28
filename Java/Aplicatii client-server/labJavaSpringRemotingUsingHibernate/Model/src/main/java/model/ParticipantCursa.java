package model;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.Objects;

public class ParticipantCursa extends Entity<Pair<String, String>> implements Serializable {
    private String idParticipant;
    private String idCursa;

    public ParticipantCursa(String idParticipant, String idCursa) {
        this.idParticipant = idParticipant;
        this.idCursa = idCursa;
    }

    public String getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(String idParticipant) {
        this.idParticipant = idParticipant;
    }

    public String getIdCursa() {
    return idCursa;
}

    public void setIdCursa(String idCursa) {
        this.idCursa = idCursa;
    }

    public Pair<String, String> getIdParticipantCursa(){
        return new Pair<String, String>(idParticipant, idCursa);
    }

    public void setIdParticipantCursa(String idParticipant, String idCursa){
        this.idParticipant = idParticipant;
        this.idCursa = idCursa;
    }

    @Override
    public String toString() {
        return "ParticipantCursa{" +
                "idParticipant='" + idParticipant + '\'' +
                ", idCursa='" + idCursa + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantCursa that = (ParticipantCursa) o;
        return Objects.equals(idParticipant, that.idParticipant) &&
                Objects.equals(idCursa, that.idCursa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idParticipant, idCursa);
    }
}
