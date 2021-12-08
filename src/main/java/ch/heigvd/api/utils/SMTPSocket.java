package ch.heigvd.api.utils;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SMTPSocket {

  private static final String LF = "\n";
  private static final String CRLF = "\r\n";

  private final Socket socket;
  private final String host;
  private final BufferedReader in;
  private final BufferedWriter out;

  public SMTPSocket(@NotNull String host, int port) throws IOException {
    socket = new Socket(host, port);
    this.host = host;
    out =
        new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
  }

  public ArrayList<SMTPCode> readCodes() throws IOException, NumberFormatException {
    ArrayList<SMTPCode> codes = new ArrayList<>();

    String line;
    boolean end = false;
    while (!end) {
      line = in.readLine();
      ;
      String[] code = line.split("-", 2);
      if (code.length != 2) {
        code = line.split(" ", 2);
        end = true;
      }
      codes.add(new SMTPCode(Integer.parseInt(code[0]), code[1]));
    }
    return codes;
  }

  public void connect() throws IOException {
    out.write("EHLO " + host + CRLF);
    out.flush();
  }

  public void send(Mail mail) throws IOException {
    out.write(getSMTPString(mail));
    out.flush();
  }

  public void quit() throws IOException {
    out.write("QUIT" + CRLF);
    out.flush();
  }

  public void close() throws IOException {
    socket.close();
  }

  private String getSMTPString(@NotNull Mail mail) {
    Group group = mail.getGroup();
    Message message = mail.getMessage();

    StringBuilder sb = new StringBuilder();

    // Mail sender
    sb.append("MAIL FROM: ")
        .append("<")
        .append(group.getSender().getEMailAddress())
        .append(">")
        .append(CRLF);

    // Mail recipients
    for (User rec : group.getRecipients())
      sb.append("RCPT TO: ").append("<").append(rec.getEMailAddress()).append(">").append(CRLF);

    sb.append("DATA").append(CRLF);
    sb.append("From: ").append(group.getSender().getEMailAddress()).append(CRLF);
    sb.append("To: ");
    for (int i = 0; i < group.getRecipients().size(); ++i) {
      if (i != 0) sb.append(", ");
      sb.append(group.getRecipients().get(i).getEMailAddress());
    }
    sb.append(CRLF); // End of "To:"

    sb.append("Content-Type: text/plain; charset=utf-8").append(CRLF); // Set encoding to UTF-8

    sb.append("Subject: =?UTF-8?B?").append(message.getBase64Subject()).append("?=").append(CRLF).append(CRLF);
    // Prevent unexpected ending if a message contrains the sequence "<CRLF>.<CRLF>"
    sb.append(message.getBody().replace(CRLF, LF));

    // End of mail
    sb.append(CRLF).append('.').append(CRLF);

    return sb.toString();
  }
}
