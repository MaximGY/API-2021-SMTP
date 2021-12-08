package ch.heigvd.api.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class FileParser {
  private static final String MESSAGE_DELIMITER = "==";
  private static final String LF = "\n";
  private static final String CRLF = "\r\n";

  public static ArrayList<Message> getMessagesFromFile(String filename) throws IOException {
    ArrayList<Message> messages = new ArrayList<>();

    Scanner scanner = new Scanner(new FileInputStream(filename), StandardCharsets.UTF_8);
    scanner.useDelimiter(MESSAGE_DELIMITER);

    while (scanner.hasNext()) {

      String rawContent = scanner.next().trim();
      int indexEndSubject = rawContent.indexOf(LF);
      if (indexEndSubject == -1) indexEndSubject = rawContent.indexOf(CRLF);

      String subject = rawContent.substring(0, indexEndSubject).replace("Subject: ", "").trim();
      String body = rawContent.substring(indexEndSubject + CRLF.length()).trim();

      messages.add(new Message(subject, body));
    }
    scanner.close();
    return messages;
  }

  public static ArrayList<User> getUsersFromFile(String filename) throws IOException {
    ArrayList<User> victims = new ArrayList<>();

    var reader = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8));
    String line;
    while (reader.ready()) {
      line = reader.readLine();
      if (!line.matches("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)"))
        throw new RuntimeException(line + " is an invalid email !");
      victims.add(new User(line));
    }
    reader.close();
    return victims;
  }

  // Inspired from :
  // https://www.tutorialspoint.com/how-to-read-the-data-from-a-properties-file-in-java
  public static Properties getPropertiesFromFile(String filename) throws IOException {
    FileInputStream fins = new FileInputStream(filename);
    Properties prop = new Properties();
    prop.load(fins);
    fins.close();
    return prop;
  }
}
