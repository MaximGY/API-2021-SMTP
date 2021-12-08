package ch.heigvd.api.utils;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class FileParser {
  private static final String MESSAGE_DELIMITER = "==";
  private static final String LF = "\n";
  private static final String CRLF = "\r\n";

  /**
   * Reads the config file containing all messages.
   *
   * @param filename The name of the file containing the messages.
   * @return An ArrayList of Messages.
   * @throws IOException If an error occurs while reading the file.
   */
  public static ArrayList<Message> getMessagesFromFile(@NotNull String filename)
      throws IOException {
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

  /**
   * Reads the config file containing all users.
   *
   * @param filename The name of the file containing the users.
   * @return An ArrayList of Users.
   * @throws IOException If an error occurs while reading the file.
   */
  public static ArrayList<User> getUsersFromFile(@NotNull String filename) throws IOException {
    ArrayList<User> victims = new ArrayList<>();

    var reader = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8));
    while (reader.ready()) {
      victims.add(new User(reader.readLine()));
    }
    reader.close();
    return victims;
  }

  // Inspired from :
  // https://www.tutorialspoint.com/how-to-read-the-data-from-a-properties-file-in-java

  /**
   * Reads the config file containing various properties.
   *
   * @param filename The name of the file containing the properties.
   * @return A Properties object containing the various entries.
   * @throws IOException If an error occurs while reading the file.
   */
  public static Properties getPropertiesFromFile(@NotNull String filename) throws IOException {
    FileInputStream fins = new FileInputStream(filename);
    Properties prop = new Properties();
    prop.load(fins);
    fins.close();
    return prop;
  }
}
