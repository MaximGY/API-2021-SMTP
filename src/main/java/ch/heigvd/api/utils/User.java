package ch.heigvd.api.utils;

import org.jetbrains.annotations.NotNull;

/** @author Maxim Golay & Perregaux Lucien */
public class User {
  private final String email;

  /**
   * Creates a new User.
   *
   * @param email The email address of the User.
   */
  public User(@NotNull String email) {
    if (!email.matches("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)"))
      throw new IllegalArgumentException("'" + email + "' is an invalid email !");
    this.email = email;
  }

  /** @return The email address of this User. */
  public String getEMailAddress() {
    return email;
  }

  @Override
  public String toString() {
    return "User{" + "email='" + email + '\'' + '}';
  }
}
