package ch.heigvd.api.utils;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SMTPSocket {

  public static final String LF = "\n";
  public static final String CRLF = "\r\n";

  private Socket socket;
  private String host;
  private BufferedReader in;
  private BufferedWriter out;

  public SMTPSocket(String host, int port) throws IOException {
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
      String[] code = line.split("-");
      if (code.length != 2) {
        code = line.split(" ");
        end = true;
      }
      codes.add(new SMTPCode(Integer.parseInt(code[0]), code[1]));
    }
    return codes;
  }

  public void connect() throws IOException {
    out.write("EHLO " + host);
  }

  public void send(Mail mail) throws IOException {
    out.write(getSMTPString(mail));
  }

  public void quit() throws IOException {
    out.write("quit");
  }

  public void close() throws IOException {

    socket.close();
  }

  private String getSMTPString(Mail mail) {
    Group group = mail.getGroup();
    Message message = mail.getMessage();

    StringBuilder sb = new StringBuilder();

    // Mail sender
    sb.append("MAIL FROM: <").append(group.getSender().getEMailAddress()).append('>').append(CRLF);

    // Mail recipients
    for (User rec : mail.getGroup().getRecipients())
      sb.append("RCPT TO: <").append(rec.getEMailAddress()).append('>').append(CRLF);

    sb.append("DATA").append(CRLF).append(message.getBody());
    sb.append("From: ").append(group.getSender().getEMailAddress()).append(CRLF);
    sb.append("To: ");
    for (int i = 0; i < mail.getGroup().getRecipients().size(); ++i) {
      if (i != 0) sb.append(", ");
      sb.append("To: ").append(mail.getGroup().getRecipients().get(i).getEMailAddress());
    }
    sb.append(CRLF);

    sb.append("Subject: ").append(mail.getMessage().getSubject()).append(CRLF);
    // Replace end of mail sequence inside the body by LF instead of CRLF
    sb.append(mail.getMessage().getBody().replace(CRLF + "." + CRLF, LF + "." + LF));

    // End of mail
    sb.append(CRLF).append('.').append(CRLF);

    return sb.toString();
  }
}
