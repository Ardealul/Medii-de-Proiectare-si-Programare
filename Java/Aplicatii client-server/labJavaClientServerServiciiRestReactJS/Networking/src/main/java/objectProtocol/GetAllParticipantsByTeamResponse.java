package objectProtocol;

import model.Participant;

public class GetAllParticipantsByTeamResponse implements Response {
    private Participant[] participants;

    public GetAllParticipantsByTeamResponse(Participant[] participants) {
        this.participants = participants;
    }

    public Participant[] getParticipants() {
        return participants;
    }
}
