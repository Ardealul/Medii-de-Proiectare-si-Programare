package dto;

import java.io.Serializable;

public class CursaDTO implements Serializable {
    private String id;
    private Integer nrPersoane;
    private Integer capMotor;

    public CursaDTO(String id, Integer nrPersoane, Integer capMotor) {
        this.id = id;
        this.nrPersoane = nrPersoane;
        this.capMotor = capMotor;
    }

    public String getId() {
        return id;
    }

    public Integer getNrPersoane() {
        return nrPersoane;
    }

    public Integer getCapMotor() {
        return capMotor;
    }

    @Override
    public String toString() {
        return "CursaDTO{" +
                "id='" + id + '\'' +
                ", nrPersoane='" + nrPersoane + '\'' +
                ", capMotor=" + capMotor +
                '}';
    }
}
