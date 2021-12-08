package ch.heigvd.api.utils;

import org.jetbrains.annotations.NotNull;

public class Mail {
  private final Group group;
  private final Message message;

  public Mail(@NotNull Group group, @NotNull Message message) {
    this.group = group;
    this.message = message;
  }

  public Group getGroup() {
    return group;
  }

  public Message getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "Mail{" + "group=" + group + ", message=" + message + '}';
  }
}
