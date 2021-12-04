package ch.heigvd.api.utils;

public class Mail {
  private final Group group;
  private final Message message;

  public Mail(Group group, Message message) {
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
