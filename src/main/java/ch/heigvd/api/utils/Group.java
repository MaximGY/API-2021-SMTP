package ch.heigvd.api.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Group {

  private final User sender;
  private final ArrayList<User> recipients;

  /**
   * Constructs a new Group.
   *
   * @param sender The person sending the Mail.
   * @param recipients The people receiving the Mail.
   * @throws IllegalArgumentException If there is less that 2 recipients.
   */
  public Group(@NotNull User sender, @NotNull ArrayList<User> recipients)
      throws IllegalArgumentException {
    if (recipients.size() < 2)
      throw new IllegalArgumentException("A group must contains at least 2 recipients !");
    this.sender = sender;
    this.recipients = recipients;
  }

  /** @return The User sending the message. */
  public User getSender() {
    return sender;
  }

  /** @return An ArrayList of Users receiving the message. */
  public ArrayList<User> getRecipients() {
    return recipients;
  }

  @Override
  public String toString() {
    return "Group{" + "sender=" + sender + ", recipients=" + recipients + '}';
  }
}
