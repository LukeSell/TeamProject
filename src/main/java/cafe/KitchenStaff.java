package cafe;

/**
 * Class to represent the kitchen staff user.
 *
 * @author thanushen
 */

public class KitchenStaff extends User {

  /**
   * Initialises a kitchen staff object.
   *
   * @param username username of kitchen staff
   * @param password password of kitchen staff
   * @param hostname hostname of kitchen staff
   */

  public KitchenStaff(String username, String password, String hostname) {
    super(username, password, hostname, UserRole.KITCHENSTAFF);
  }

  /**
   * Initialises a kitchen staff object.
   *
   * @param username username of kitchen staff
   * @param hostname hostname of kitchen staff
   */

  public KitchenStaff(String username, String hostname) {
    super(username, hostname, UserRole.KITCHENSTAFF);
  }

  /**
   * Initialises a kitchen staff object.
   *
   * @param hostname hostname of kitchen staff
   */

  public KitchenStaff(String hostname) {
    super(hostname, UserRole.KITCHENSTAFF);
  }

}
