package ch.heigvd.api.utils;

public class User {

    private final String email;

    public User(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return email;
    }
}
