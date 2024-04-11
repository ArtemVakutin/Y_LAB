package model;

import lombok.Data;

import java.util.Objects;

@Data
public class DefaultUser {

    private String login;

    private String password;

    private Role role = Role.SIMPLE_USER;

    public DefaultUser() {
    }

    public DefaultUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public enum Role {
        SIMPLE_USER, ADMIN
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultUser that = (DefaultUser) o;
        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
