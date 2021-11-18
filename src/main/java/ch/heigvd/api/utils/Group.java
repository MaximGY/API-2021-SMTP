package ch.heigvd.api.utils;

import java.util.ArrayList;

public class Group {

    private final User sender;
    private final ArrayList<User> recipients;

    Group(User sender, ArrayList<User> recipients) {
        this.sender = sender;
        this.recipients = recipients;
    }

    public User getSender() {
        return sender;
    }

    public ArrayList<User> getRecipients() {
        return recipients;
    }
}
