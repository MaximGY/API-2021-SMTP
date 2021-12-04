package ch.heigvd.api.utils;

import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class Message {

  private final String subject;
  private final String body;

  public Message(String subject, String body) {
    this.subject = subject;
    this.body = body;
  }

  public String getSubject() {
    return subject;
  }

  public String getBase64Subject() {
    return new String(Base64.getEncoder().encode(subject.getBytes(StandardCharsets.UTF_8)));
  }

  public String getBody() {
    return body;
  }

  @Override
  public String toString() {
    return "Message{" + "subject='" + subject + '\'' + ", body='" + body + '\'' + '}';
  }
}
