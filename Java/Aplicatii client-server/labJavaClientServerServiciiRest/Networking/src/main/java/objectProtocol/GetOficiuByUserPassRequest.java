package objectProtocol;

public class GetOficiuByUserPassRequest implements Request {
    private String username;
    private String password;

    public GetOficiuByUserPassRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
