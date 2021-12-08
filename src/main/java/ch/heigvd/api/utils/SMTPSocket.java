package ch.heigvd.api.utils;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Represents a connection to a SMTP server.
 */
public class SMTPSocket {

  private static final String LF = "\n";
  private static final String CRLF = "\r\n";

  private final Socket socket;
  private final String host;
  private final BufferedReader in;
  private final BufferedWriter out;

  /**
   * Creates a new connection to a SMTP server.
   *
   * @param host The address of the server.
   * @param port The port of the server.
   * @throws IOException If errors occurs when opening the socket.
   */
  public SMTPSocket(@NotNull String host, int port) throws IOException {
    socket = new Socket(host, port);
    this.host = host;
    out =
        new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
  }

  /**
   * Parses all received codes from the server as SMTPCodes.
   *
   * @return An ArrayList of SMTPCodes send back by the sever.
   * @throws IOException If an error occurs while reading the answer.
   * @throws NumberFormatException If the code send back by the server makes no sense.
   */
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

  /**
   * Opens the connection to the server.
   *
   * @throws IOException If something went wrong.
   */
  public void connect() throws IOException {
    out.write("EHLO " + host + CRLF);
    out.flush();
  }

  /**
   * Send the given Mail to the server.
   *
   * @param mail The Mail to send to the server.
   * @throws IOException If something went wrong.
   */
  public void send(Mail mail) throws IOException {
    out.write(getSMTPString(mail));
    out.flush();
  }

  /**
   * Terminates the communication with the server protocole-wise.
   *
   * @throws IOException If something went wrong.
   */
  public void quit() throws IOException {
    out.write("QUIT" + CRLF);
    out.flush();
  }

  /**
   * Closes the underlying socket.
   *
   * @throws IOException If something went wrong.
   */
  public void close() throws IOException {
    socket.close();
  }

  /**
   * Wraps the Mail with its required SMTP commands.
   *
   * @param mail The Mail to prepare for sending.
   * @return A String containing the SMTP command to give to the server.
   */
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
