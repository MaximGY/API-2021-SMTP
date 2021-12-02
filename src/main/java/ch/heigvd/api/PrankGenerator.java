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
    SMTPSocket smtpSocket = null;
    ArrayList<SMTPCode> codes;

    try {

      Properties prop = FileParser.getPropertiesFromFile(args[0]);
      ArrayList<User> victims = FileParser.getUsersFromFile(args[1]);
      ArrayList<Message> messages = FileParser.getMessagesFromFile(args[2]);
      ArrayList<Group> groups =
          generateGroups(victims, Integer.parseInt(prop.getProperty("nbgroups")));
      ArrayList<Mail> mails = generateMails(groups, messages);

      smtpSocket =
          new SMTPSocket(prop.getProperty("host"), Integer.parseInt(prop.getProperty("port")));

      codes = smtpSocket.readCodes();
      printCodes(codes);
      smtpSocket.connect();
      codes = smtpSocket.readCodes();
      printCodes(codes);

      if (codes.get(codes.size() - 1).getCode() != 250)
        throw new Exception("Unable to read extensions !");

      for (Mail m : mails) {
        smtpSocket.send(m);
        codes = smtpSocket.readCodes();
        printCodes(codes);
      }

      smtpSocket.quit();

    } catch (Exception e) {
      System.err.println("Error : " + e.getMessage());
    } finally {
      if (smtpSocket != null) {
        try {
          smtpSocket.close();
        } catch (IOException ignored) {
        }
      }
    }
  }

  private static ArrayList<Group> generateGroups(ArrayList<User> victims, int nbGroups) {
    if (nbGroups <= 0)
      throw new RuntimeException("The number of groups must be greater than zéro.");

    ArrayList<Group> groups = new ArrayList<>(nbGroups);
    for (int i = 0; i < nbGroups; ++i) groups.add(new Group());

    int nbVictimsPerGroup = victims.size() / nbGroups;
    if (nbVictimsPerGroup < 3) throw new RuntimeException("Not enough victims !");

    final int shift = random.nextInt(victims.size());

    for (int i = 0; i < victims.size(); ++i) {
      // Séléctionne un groupe séquentiellement.
      Group group = groups.get((i + 1) % nbGroups);
      // On prend un utilisateur au hasard et on le rajoute au groupe.
      User user = victims.get((i + shift) % victims.size());
      if (group.getSender() == null) group.setSender(user);
      else group.addRecipient(user);
    }
    return groups;
  }

  private static ArrayList<Mail> generateMails(
      ArrayList<Group> groups, ArrayList<Message> messages) {
    ArrayList<Mail> mails = new ArrayList<>(groups.size());
    final int shift = random.nextInt(messages.size());
    for (int i = 0; i < groups.size(); ++i) {
      mails.add(new Mail(groups.get(i), messages.get((i + shift) % messages.size())));
    }
    return mails;
  }

  private static void printCodes(ArrayList<SMTPCode> codes) {
      for (SMTPCode c : codes)
        System.out.println(c);
  }
}
