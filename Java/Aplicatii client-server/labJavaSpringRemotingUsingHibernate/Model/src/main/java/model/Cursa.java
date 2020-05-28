package model;

import java.io.Serializable;
import java.util.Objects;

public class Cursa extends Entity<String> implements Serializable {
    private Integer nrPersoane;
    private Integer capacitateMotor;

    public Cursa(Integer nrPersoane, Integer capacitateMotor) {
        this.nrPersoane = nrPersoane;
        this.capacitateMotor = capacitateMotor;
    }

    public String getId() {
        return super.getId();
    }

    public Integer getCapacitateMotor() {
        return capacitateMotor;
    }

    public void setCapacitateMotor(Integer capacitateMotor) {
        this.capacitateMotor = capacitateMotor;
    }

    public Integer getNrPersoane() {
        return nrPersoane;
    }

    public void setNrPersoane(Integer nrPersoane) {
        this.nrPersoane = nrPersoane;
    }

    @Override
    public String toString() {
        return "Cursa{" +
                "id=" + getId() +
                ", nrPersoane=" + nrPersoane +
                ", capacitateMotor=" + capacitateMotor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cursa cursa = (Cursa) o;
        return Objects.equals(getId(), cursa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), nrPersoane, capacitateMotor);
    }
}
