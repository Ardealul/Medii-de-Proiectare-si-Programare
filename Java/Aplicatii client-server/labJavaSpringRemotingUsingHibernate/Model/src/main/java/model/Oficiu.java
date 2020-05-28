package model;

import java.io.Serializable;
import java.util.Objects;

public class Oficiu extends Entity<String> implements Serializable {
    private String username;
    private String password;

    public Oficiu(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Oficiu() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Oficiu{" +
                "id='" + getId() + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Oficiu oficiu = (Oficiu) o;
        return Objects.equals(username, oficiu.username) &&
                Objects.equals(password, oficiu.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
