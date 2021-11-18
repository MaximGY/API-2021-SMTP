package ch.heigvd.api.utils;

public class Mail {

    public static String LF = "\n";
    public static String CRLF = "\r\n";

    private final Group group;
    private final String body;


    Mail(Group group, String body) {

        this.group = group;
        this.body = body;
    }

    public String getSMTPCommand() {

        StringBuilder sb = new StringBuilder();

        // Mail sender
        sb.append("MAIL FROM: <").append(group.getSender().getMail()).append('>').append(CRLF);

        // Mail recipients
        for (User rec: group.getRecipients())
            sb.append("RCPT TO: <").append(rec.getMail()).append('>').append(CRLF);

        // TODO: add headers to body
        sb.append("DATA").append(CRLF).append(body);

        // End of mail
        sb.append(CRLF).append('.').append(CRLF);

        return sb.toString();
    }
}
