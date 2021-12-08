package ch.heigvd.api.utils;

import org.jetbrains.annotations.NotNull;

/** @author Maxim Golay & Perregaux Lucien */
public class Mail {
  private final Group group;
  private final Message message;

  /**
   * Creates a new Mail.
   *
   * @param group The Group of people to prank.
   * @param message The Messages contained in the Mail.
   */
  public Mail(@NotNull Group group, @NotNull Message message) {
    this.group = group;
    this.message = message;
  }

  /** @return The Group to prank. */
  public Group getGroup() {
    return group;
  }

  /** @return The Message to send. */
  public Message getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "Mail{" + "group=" + group + ", message=" + message + '}';
  }
}
