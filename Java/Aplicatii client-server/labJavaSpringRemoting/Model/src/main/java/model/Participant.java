package model;

import java.io.Serializable;
import java.util.Objects;

public class Participant extends Entity<String> implements Serializable {
    private String nume;
    private String echipa;
    private Integer capacitateMotor;

    public Participant(String nume, String echipa, Integer capacitateMotor) {
        this.nume = nume;
        this.echipa = echipa;
        this.capacitateMotor = capacitateMotor;
    }

    @Override
    public String getId() {
        return super.getId();
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEchipa() {
        return echipa;
    }

    public void setEchipa(String echipa) {
        this.echipa = echipa;
    }

    public Integer getCapacitateMotor() {
        return capacitateMotor;
    }

    public void setCapacitateMotor(Integer capacitateMotor) {
        this.capacitateMotor = capacitateMotor;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id='" + getId() + '\'' +
                ", nume='" + nume + '\'' +
                ", echipa='" + echipa + '\'' +
                ", capacitateMotor=" + capacitateMotor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(nume, that.nume) &&
                Objects.equals(echipa, that.echipa) &&
                Objects.equals(capacitateMotor, that.capacitateMotor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), nume, echipa, capacitateMotor);
    }
}
