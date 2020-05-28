package objectProtocol;

import dto.ParticipantDTO;

public class GetParticipantResponse implements Response {
    private ParticipantDTO participantDTO;

    public GetParticipantResponse(ParticipantDTO participantDTO) {
        this.participantDTO = participantDTO;
    }

    public ParticipantDTO getParticipantDTO() {
        return participantDTO;
    }
}
