package objectProtocol;

import dto.ParticipantCursaDTO;

public class AddParticipantCursaRequest implements Request {
    private ParticipantCursaDTO participantCursaDTO;

    public AddParticipantCursaRequest(ParticipantCursaDTO participantCursaDTO) {
        this.participantCursaDTO = participantCursaDTO;
    }

    public ParticipantCursaDTO getParticipantCursaDTO() {
        return participantCursaDTO;
    }
}
