package ch.heigvd.api.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class Message {

  private final String subject;
  private final String body;

  /**
   * Creates a new Message.
   *
   * @param subject The subject of the mail.
   * @param body The body of the mail.
   */
  public Message(@NotNull String subject, @NotNull String body) {
    this.subject = subject;
    this.body = body;
  }

  /** @return The subject of this Message. */
  public String getSubject() {
    return subject;
  }

  /** @return The subject of this Message encoded in base 64. */
  public String getBase64Subject() {
    return new String(Base64.getEncoder().encode(subject.getBytes(StandardCharsets.UTF_8)));
  }

  /** @return The subject of this Message. */
  public String getBody() {
    return body;
  }

  @Override
  public String toString() {
    return "Message{" + "subject='" + subject + '\'' + ", body='" + body + '\'' + '}';
  }
}
