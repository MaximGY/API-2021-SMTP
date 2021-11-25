package ch.heigvd.api;

import ch.heigvd.api.utils.*;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PrankGenerator {

  public static void main(String[] args) {
    if (args.length != 3) {
      System.err.println("Usage: ./main {settings.properties} {victims.utf8} {messages.utf8}");
      return;
    }

    try {

      Properties prop = FileParser.getPropertiesFromFile(args[0]);
      ArrayList<User> victims = FileParser.getUsersFromFile(args[1]);
      ArrayList<Message> message = FileParser.getMessagesFromFile(args[2]);

      Socket client = new Socket(prop.getProperty("host"), Integer.parseInt(prop.getProperty("port")));

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
}
