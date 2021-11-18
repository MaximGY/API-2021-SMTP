package ch.heigvd.api.utils;

import java.util.ArrayList;

public class Mail {

    public static String LF = "\n";
    public static String CRLF = "\r\n";

    private final User sender;
    private final ArrayList<User> recipients;
    private final String body;




    Mail(User sender, ArrayList<User> recipients, String body) {

        this.sender = sender;
        this.recipients = recipients;
        this.body = body;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        // Mail sender
        sb.append("MAIL FROM: <").append(sender.toString()).append('>').append(CRLF);

        // Mail recipients
        for (User rec: recipients)
            sb.append("RCPT TO: <").append(rec.toString()).append('>').append(CRLF);

        // TODO: add headers to body
        sb.append("DATA").append(CRLF).append(body);

        // End of mail
        sb.append(CRLF).append('.').append(CRLF);

        return sb.toString();
    }
}
