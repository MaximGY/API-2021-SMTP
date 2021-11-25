package ch.heigvd.api;

import ch.heigvd.api.utils.*;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PrankGenerator {
  static final Random random = new Random();

  public static void main(String[] args) {
    if (args.length != 3) {
      System.err.println("Usage: ./main {settings.properties} {victims.utf8} {messages.utf8}");
      return;
    }

    try {
      Properties prop = FileParser.getPropertiesFromFile(args[0]);
      ArrayList<User> victims = FileParser.getUsersFromFile(args[1]);
      ArrayList<Message> messages = FileParser.getMessagesFromFile(args[2]);
      int nbGroups = 3;
      ArrayList<Group> groups = generateGroups(victims, nbGroups);

      ArrayList<Mail> mails = generateMails(groups, messages);

      Socket client =
          new Socket(prop.getProperty("host"), Integer.parseInt(prop.getProperty("port")));

      for (Mail m : mails) {
        // m.send(client);
      }

      client.close();

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  private static void sendMail(Socket client, Mail mail) throws IOException {
    if (client == null) throw new IllegalArgumentException("Client socket is null !");

    String msg = "";

    var in =
        new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
    var out =
        new BufferedWriter(
            new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8));
  }

  private static ArrayList<Group> generateGroups(ArrayList<User> victims, int nbGroups) {
    if (nbGroups <= 0)
      throw new RuntimeException("The number of groups must be greater than zéro.");

    ArrayList<Group> groups = new ArrayList<>(nbGroups);

    int nbVictimsPerGroup = victims.size() / nbGroups;
    if (nbVictimsPerGroup < 3) throw new RuntimeException("Not enough victims !");

    final int shift = random.nextInt();

    for (int i = 0; i < victims.size(); ++i) {
      Group group = groups.get(i % nbGroups);
      User user = victims.get((i + shift) % victims.size());
      if (group.getSender() == null) group.setSender(user);
      else group.addRecipient(user);
    }
    return groups;
  }

  private static ArrayList<Mail> generateMails(
      ArrayList<Group> groups, ArrayList<Message> messages) {
    ArrayList<Mail> mails = new ArrayList<>(groups.size());
    final int shift = random.nextInt();
    for (int i = 0; i < groups.size(); ++i) {
      mails.add(new Mail(groups.get(i), messages.get((i + shift) % messages.size())));
    }
    return mails;
  }
}
