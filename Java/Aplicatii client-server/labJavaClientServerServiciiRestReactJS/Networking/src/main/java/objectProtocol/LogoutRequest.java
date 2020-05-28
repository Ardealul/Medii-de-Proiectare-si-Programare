package objectProtocol;

import dto.OficiuDTO;

public class LogoutRequest implements Request {
    private OficiuDTO oficiuDTO;

    public LogoutRequest(OficiuDTO oficiuDTO) {
        this.oficiuDTO = oficiuDTO;
    }

    public OficiuDTO getOficiuDTO() {
        return oficiuDTO;
    }
}
