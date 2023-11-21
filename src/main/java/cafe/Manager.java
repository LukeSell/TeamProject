package cafe;

/**
 * This class represents the manager object and inherits from the User object.
 *
 * @author thanushen
 */

public class Manager extends User {

  /**
   * Initialises a manager object.
   * @param username username of user.
   * @param password password of user.
   * @param hostname hostname of user.
   */
  public Manager(String username, String password, String hostname) {
    super(username, password, hostname, UserRole.MANAGER);
  }

  /**
   * Initialises a manager object.
   * @param username username of user
   * @param hostname hostname of user 
   */

  public Manager(String username, String hostname) {
    super(username, hostname, UserRole.MANAGER);
  }

  /**
   * Initialises a manager object.
   * @param hostname hostname of user
   */

  public Manager(String hostname) {
    super(hostname, UserRole.MANAGER);
  }

}
