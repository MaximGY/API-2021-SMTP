package ch.heigvd.api.utils;

import java.util.ArrayList;

public class Group {

  private User sender;
  private ArrayList<User> recipients;

  public Group() {
    recipients = new ArrayList<>();
  }

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

  public void setSender(User sender) {
    this.sender = sender;
  }

  public void addRecipient(User recipient) {
    recipients.add(recipient);
  }
}
