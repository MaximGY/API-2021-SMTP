package ch.heigvd.api.utils;

public class SMTPCode {
    private final int code;
    private final String value;

    public SMTPCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
