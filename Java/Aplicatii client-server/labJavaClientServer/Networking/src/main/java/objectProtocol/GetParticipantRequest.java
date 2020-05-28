package objectProtocol;

public class GetParticipantRequest implements Request {
    private String nume;
    private String echipa;
    private Integer capMotor;

    public GetParticipantRequest(String nume, String echipa, Integer capMotor) {
        this.nume = nume;
        this.echipa = echipa;
        this.capMotor = capMotor;
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
