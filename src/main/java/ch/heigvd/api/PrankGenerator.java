package ch.heigvd.api;

import java.io.IOException;
import java.net.Socket;

public class PrankGenerator {
  public static void main(String[] args) {
    if (args.length < 2) {
      System.err.println("Usage: main {host} {port}");
      return;
    }

    String host = args[0];
    int port;
    try {
      port = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      System.err.println(e.getMessage());
      return;
    }

    try {
      Socket client = new Socket(host, port);

      sendMail(client);

      client.close();

    } catch (IOException | IllegalArgumentException e) {
      System.err.println(e.getMessage());
    }
  }

  private static void sendMail(Socket client)
  {
    if (client == null) throw new IllegalArgumentException("Client socker is null !");
  }
}
