package ch.heigvd.api;

import ch.heigvd.api.utils.*;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PrankGenerator {
  private static final String DELIMITER = "==";

  public static void main(String[] args) {
    if (args.length < 4) {
      System.err.println("Usage: ./main {host} {port} {victims.txt} {mail_body.txt}");
      return;
    }

    try {

      String host = args[0];
      int port = Integer.parseInt(args[1]);
      ArrayList<User> victims = getVictims(args[2]);
      ArrayList<Message> message = getMessages(args[3]);

      Socket client = new Socket(host, port);

      sendMail(client);

      client.close();

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  private static void sendMail(Socket client) throws IOException {
    if (client == null) throw new IllegalArgumentException("Client socket is null !");

    String msg = "";

    var in =
        new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
    var out =
        new BufferedWriter(
            new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8));
  }

  private static ArrayList<User> getVictims(String filename) throws IOException {
    ArrayList<User> victims = new ArrayList<>();

    var reader = new BufferedReader(new FileReader("myfile.txt", StandardCharsets.UTF_8));
    String line;
    while (reader.ready()) {
      line = reader.readLine();
      if (!line.matches("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)"))
        throw new RuntimeException(line + "is an invalid email !");
      victims.add(new User(line));
    }

    return victims;
  }

  private static ArrayList<Message> getMessages(String filename) throws IOException {
    ArrayList<Message> messages = new ArrayList<>();

    Scanner scanner = new Scanner(new File(filename), StandardCharsets.UTF_8);
    scanner.useDelimiter(DELIMITER);

    while (scanner.hasNext())
    {
      String[] lines = scanner.next().split(Mail.LF);
      String subject = lines[0].contains("Subject:") ? lines[0].replace("Subject:", "").trim() : "<>";

      StringBuilder msgBuilder = new StringBuilder();
      for (int i = 1; i < lines.length; ++i)
      {
        msgBuilder.append(lines[i]);
        msgBuilder.append(Mail.LF);
      }
      String message = msgBuilder.toString();

      //messages.add(new Message(subject, message));
    }

    return messages;
  }
}
