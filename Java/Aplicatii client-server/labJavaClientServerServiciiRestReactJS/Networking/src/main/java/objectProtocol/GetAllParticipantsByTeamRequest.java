package objectProtocol;

public class GetAllParticipantsByTeamRequest implements Request {
    private String echipa;

    public GetAllParticipantsByTeamRequest(String echipa) {
        this.echipa = echipa;
    }

    public String getEchipa() {
        return echipa;
    }
}
