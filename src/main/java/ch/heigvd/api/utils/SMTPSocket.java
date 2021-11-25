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
}
