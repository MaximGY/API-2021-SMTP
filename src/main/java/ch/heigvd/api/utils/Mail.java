package ch.heigvd.api.utils;

public class Mail {

    public static final String LF = "\n";
    public static final String CRLF = "\r\n";

    private final Group group;
    private final Message message;


    public Mail(Group group, Message message) {

        this.group = group;
        this.message = message;
    }

    public String getSMTPCommand() {

        StringBuilder sb = new StringBuilder();

        // Mail sender
        sb.append("MAIL FROM: <").append(group.getSender().getMail()).append('>').append(CRLF);

        // Mail recipients
        for (User rec: group.getRecipients())
            sb.append("RCPT TO: <").append(rec.getMail()).append('>').append(CRLF);

        // TODO: add headers to body
        sb.append("DATA").append(CRLF).append(message.getBody());

        // End of mail
        sb.append(CRLF).append('.').append(CRLF);

        return sb.toString();
    }
}
