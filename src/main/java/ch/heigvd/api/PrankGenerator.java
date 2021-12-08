package ch.heigvd.api;

import ch.heigvd.api.utils.*;

import java.io.*;
import java.util.*;

/** @author Maxim Golay & Perregaux Lucien */
public class PrankGenerator {
  static final Random random = new Random();

  /**
   * Application entrypoint.
   *
   * @param args The various config files path.
   */
  public static void main(String[] args) {
    if (args.length != 3) {
      System.err.println(
          "Usage: PrankGenerator {settings.properties} {victims.utf8} {messages.utf8}");
      return;
    }

    Properties prop;
    ArrayList<User> victims;
    ArrayList<Message> messages;

    try {
      prop = FileParser.getPropertiesFromFile(args[0]);
      victims = FileParser.getUsersFromFile(args[1]);
      messages = FileParser.getMessagesFromFile(args[2]);

    } catch (IOException e) {
      System.err.println(e.getMessage());
      return;
    }

    ArrayList<Group> groups =
        generateGroups(victims, Integer.parseInt(prop.getProperty("nbgroups")));
    ArrayList<Mail> mails = generateMails(groups, messages);

    ArrayList<SMTPCode> codes;

    try (SMTPSocket smtpSocket =
        new SMTPSocket(prop.getProperty("host"), Integer.parseInt(prop.getProperty("port")))) {
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
        System.out.println("Mail sent: " + m);
        printCodes(codes);
      }

      smtpSocket.quit();

    } catch (Exception e) {
      System.err.println("Error : " + e.getMessage());
    }
  }

  /**
   * Generate random groups according to the loaded config.
   *
   * @param victims An array of User receiving a prank
   * @param nbGroups The number of groups to make
   * @return An ArrayList of Groups.
   */
  private static ArrayList<Group> generateGroups(ArrayList<User> victims, int nbGroups) {
    if (nbGroups <= 0)
      throw new RuntimeException("The number of groups must be greater than zero.");

    int nbVictimsPerGroup = victims.size() / nbGroups;
    if (nbVictimsPerGroup < 3) throw new RuntimeException("Not enough victims !");

    final int shift = random.nextInt(victims.size());

    ArrayList<ArrayList<User>> users = new ArrayList<>(nbGroups);
    for (int i = 0; i < nbGroups; ++i) users.add(new ArrayList<>());

    for (int i = 0; i < victims.size(); ++i) {
      // On prend un utilisateur au hasard et on le rajoute à la liste.
      User user = victims.get((i + shift) % victims.size());
      users.get(i % nbGroups).add(user);
    }
    ArrayList<Group> groups = new ArrayList<>(nbGroups);
    for (ArrayList<User> u : users) {
      // On crée les groupes à l'aide des utilisateurs déjà répartis aléatoirement
      // Pour une raison d'architecture, on fait une copie de la liste
      groups.add(new Group(u.get(0), new ArrayList<>(u.subList(1, u.size()))));
    }
    return groups;
  }

  /**
   * Create random Mails from the given Messages and Groups.
   *
   * @param groups The various Groups to target.
   * @param messages The pool of messages to pick from.
   * @return An ArrayList of Mails generated.
   */
  private static ArrayList<Mail> generateMails(
      ArrayList<Group> groups, ArrayList<Message> messages) {
    ArrayList<Mail> mails = new ArrayList<>(groups.size());
    final int shift = random.nextInt(messages.size());
    for (int i = 0; i < groups.size(); ++i) {
      mails.add(new Mail(groups.get(i), messages.get((i + shift) % messages.size())));
    }
    return mails;
  }

  /**
   * Prints all codes received from the server.
   *
   * @param codes And ArrayList of SMTPCodes to print.
   */
  private static void printCodes(ArrayList<SMTPCode> codes) {
    for (SMTPCode c : codes) System.out.println(c);
  }
}
