package objectProtocol;

public class GetCursaRequest implements Request {
    private String idCursa;

    public GetCursaRequest(String idCursa) {
        this.idCursa = idCursa;
    }

    public String getIdCursa() {
        return idCursa;
    }
}
