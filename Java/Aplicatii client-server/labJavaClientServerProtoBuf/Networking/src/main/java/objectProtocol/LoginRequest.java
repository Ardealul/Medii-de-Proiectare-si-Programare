package objectProtocol;

import dto.OficiuDTO;

public class LoginRequest implements Request {
    private OficiuDTO oficiuDTO ;

    public LoginRequest(OficiuDTO oficiuDTO) {
        this.oficiuDTO = oficiuDTO;
    }

    public OficiuDTO getOficiuDTO() {
        return oficiuDTO;
    }
}
