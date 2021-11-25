package ch.heigvd.api.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class FileParser {
    private static final String DELIMITER = "==";

    public static ArrayList<Message> getMessagesFromFile(String filename) throws IOException {
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

    public static ArrayList<User> getUsersFromFile(String filename) throws IOException {
        ArrayList<User> victims = new ArrayList<>();

        var reader = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8));
        String line;
        while (reader.ready()) {
            line = reader.readLine();
            if (!line.matches("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)"))
                throw new RuntimeException(line + "is an invalid email !");
            victims.add(new User(line));
        }
        return victims;
    }
}
