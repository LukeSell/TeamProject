package cafe;

/**
 * Class that represents users as a whole and all information required by user objects and classes
 * that inherit it.
 *
 * @author thanushen
 */

public class User {

  private final String username;
  private final String password;
  private final String hostname;
  private final UserRole role;

  /**
   * Initialises a user object.
   *
   * @param username for the database
   * @param password for the database
   * @param hostName for the database
   * @param userRole the role of the user
   */

  protected User(String username, String password, String hostName, UserRole userRole) {
    this.username = username;
    this.hostname = hostName;
    this.password = password;
    this.role = UserRole.CUSTOMER;
  }

  /**
   * Initialises a user object.
   *
   * @param username for the database
   * @param userRole the role of the user
   */

  protected User(String username, String host, UserRole userRole) {
    this.username = username;
    this.password = "";
    this.hostname = host;
    this.role = userRole;
  }

  /**
   * Initialises a user object.
   *
   * @param userRole the role of the user
   */

  protected User(String host, UserRole userRole) {
    this.username = host;
    this.password = "";
    this.hostname = host;
    this.role = userRole;
  }

  /**
   * Returns username.
   *
   * @return string with username
   */

  protected String getUsername() {
    return username;
  }

  /**
   * Returns hostname.
   *
   * @return string with hostname
   */

  protected String getHostname() {
    return hostname;
  }

  /**
   * Returns password.
   *
   * @return string with password
   */

  protected String getPassword() {
    return password;
  }

  /**
   * Returns role.
   *
   * @return user role
   */

  protected UserRole getRole() {
    return role;
  }

  /**
   * User to string.
   *
   * @return user username
   */

  @Override
  public String toString() {
    return username;
  }
}