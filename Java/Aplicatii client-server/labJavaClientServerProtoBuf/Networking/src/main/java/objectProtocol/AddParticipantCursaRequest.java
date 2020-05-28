package objectProtocol;

import model.ParticipantCursa;

public class AddParticipantCursaRequest implements Request {
    private ParticipantCursa participantCursa;

    public AddParticipantCursaRequest(ParticipantCursa participantCursa) {
        this.participantCursa = participantCursa;
    }

    public ParticipantCursa getParticipantCursa() {
        return participantCursa;
    }
}
