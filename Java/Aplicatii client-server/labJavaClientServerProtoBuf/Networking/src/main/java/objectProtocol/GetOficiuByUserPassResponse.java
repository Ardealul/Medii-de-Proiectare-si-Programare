package objectProtocol;

import dto.OficiuDTO;

public class GetOficiuByUserPassResponse implements Response {
    private OficiuDTO oficiuDTO;

    public GetOficiuByUserPassResponse(OficiuDTO oficiuDTO) {
        this.oficiuDTO = oficiuDTO;
    }

    public OficiuDTO getOficiuDTO() {
        return oficiuDTO;
    }
}
