package dto;

import java.io.Serializable;

public class ParticipantDTO implements Serializable {
    private String id;
    private String nume;
    private String echipa;
    private Integer capMotor;

    public ParticipantDTO(String id, String nume, String echipa, Integer capMotor) {
        this.id = id;
        this.nume = nume;
        this.echipa = echipa;
        this.capMotor = capMotor;
    }

    public String getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getEchipa() {
        return echipa;
    }

    public Integer getCapMotor() {
        return capMotor;
    }
}
