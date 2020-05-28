package objectProtocol;

import dto.CursaDTO;

public class GetAllCursaResponse implements Response {
    private CursaDTO[] curse;

    public GetAllCursaResponse(CursaDTO[] curse) {
        this.curse = curse;
    }

    public CursaDTO[] getCurse() {
        return curse;
    }
}
