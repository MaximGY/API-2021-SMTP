package ch.heigvd.api.utils;

public class User {

    private final String email;

    User(String email) {
        this.email = email;
    }

    public String getMail() {
        return email;
    }
}
