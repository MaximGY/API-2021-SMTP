package ch.heigvd.api.utils;

import java.io.IOException;
import java.net.Socket;

public class SMTPSocket {

    public static final String LF = "\n";
    public static final String CRLF = "\r\n";

    private Socket socket;

    public SMTPSocket(String host, int port) throws IOException {
        socket = new Socket(host, port);
    }

    public void send(Mail mail)
    {
        Group group = mail.getGroup();
        Message message = mail.getMessage();

        StringBuilder sb = new StringBuilder();

        // Mail sender
        sb.append("MAIL FROM: <").append(group.getSender().getEMailAddress()).append('>').append(CRLF);

        // Mail recipients
        for (User rec : mail.getGroup().getRecipients())
            sb.append("RCPT TO: <").append(rec.getEMailAddress()).append('>').append(CRLF);

        // TODO: add headers to body
        sb.append("DATA").append(CRLF).append(message.getBody());

        // End of mail
        sb.append(CRLF).append('.').append(CRLF);

        return;
    }
}
