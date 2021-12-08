package ch.heigvd.api.utils;

public class SMTPCode {
  private final int code;
  private final String value;

  /**
   * Creates a new SMTPCode.
   *
   * @param code The code returned by the server.
   * @param value The value linked to the code.
   */
  public SMTPCode(int code, String value) {
    this.code = code;
    this.value = value;
  }

  /** @return The code returned by the server. */
  public int getCode() {
    return code;
  }

  /** @return The value linked to the code. */
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return code + " " + value;
  }
}
