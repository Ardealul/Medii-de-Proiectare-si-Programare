package objectProtocol;

import model.Participant;

public class GetAllParticipantsResponse implements Response {
    private Participant[] participants;

    public GetAllParticipantsResponse(Participant[] participants) {
        this.participants = participants;
    }

    public Participant[] getParticipants() {
        return participants;
    }
}
