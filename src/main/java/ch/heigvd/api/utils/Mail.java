package ch.heigvd.api.utils;

import java.util.ArrayList;

public class Mail {

    private final User sender;
    private final ArrayList<User> recipients;
    private final String body;

    Mail(User sender, ArrayList<User> recipients, String body) {

        this.sender = sender;
        this.recipients = recipients;
        this.body = body;
    }

    @Override
    public String toString() {

        return "Ceci est un mail";
    }
}
