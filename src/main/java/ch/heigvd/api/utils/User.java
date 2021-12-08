package ch.heigvd.api.utils;

import org.jetbrains.annotations.NotNull;

public class User {
  private final String email;

  public User(@NotNull String email) {
    // TODO: Vérifier l'email ici
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
