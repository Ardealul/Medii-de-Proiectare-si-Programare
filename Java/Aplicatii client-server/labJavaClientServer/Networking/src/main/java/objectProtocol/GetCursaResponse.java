package objectProtocol;

import model.Cursa;

public class GetCursaResponse implements Response {
    private Cursa cursa;

    public GetCursaResponse(Cursa cursa) {
        this.cursa = cursa;
    }

    public Cursa getCursa() {
        return cursa;
    }
}
