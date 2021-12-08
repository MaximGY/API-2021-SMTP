package ch.heigvd.api.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Group {

  private User sender;
  private final ArrayList<User> recipients;

  public Group() {
    recipients = new ArrayList<>();
  }

  public Group(@NotNull User sender, @NotNull ArrayList<User> recipients) {
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

  @Override
  public String toString() {
    return "Group{" + "sender=" + sender + ", recipients=" + recipients + '}';
  }
}
