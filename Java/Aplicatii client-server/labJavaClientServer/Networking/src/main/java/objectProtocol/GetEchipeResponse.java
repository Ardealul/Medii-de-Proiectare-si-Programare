package objectProtocol;

public class GetEchipeResponse implements Response {
    private String echipe;

    public GetEchipeResponse(String echipe) {
        this.echipe = echipe;
    }

    public String getEchipe() {
        return echipe;
    }
}
