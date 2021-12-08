package ch.heigvd.api.utils;

import org.jetbrains.annotations.NotNull;

public class User {
  private final String email;

  public User(@NotNull String email) {
    if (!email.matches("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)"))
      throw new IllegalArgumentException("'" + email + "' is an invalid email !");
    this.email = email;
  }

  public String getEMailAddress() {
    return email;
  }

  @Override
  public String toString() {
    return "User{" + "email='" + email + '\'' + '}';
  }
}
