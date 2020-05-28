package objectProtocol;

import dto.CursaDTO;

public class UpdateCursaRequest implements Request {
    private String idCursa;
    private CursaDTO cursaDTO;

    public UpdateCursaRequest(String idCursa, CursaDTO cursaDTO) {
        this.idCursa = idCursa;
        this.cursaDTO = cursaDTO;
    }

    public String getIdCursa() {
        return idCursa;
    }

    public CursaDTO getCursaDTO() {
        return cursaDTO;
    }
}
