package cafe;

/**
 * Class that is used to generate user objects for the system. Also checks if user login is
 * correct and exists in database.
 */

public class UserFactory {

  private static DatabaseConnection database = DatabaseConnection.getDatabase();

  private UserFactory() {

  }

  /**
   * Method to check user against waiter login database, if login found a user object is returned,
   * else null is returned.
   *
   * @param username Inputted username.
   * @param password Inputted password.
   * @return waiter if correct login or null if not.
   */

  public static Waiter getWaiterLogin(String username, String password) {
    for (Waiter waiter : database.waiterLoginList()) {
      if (username.equals(waiter.getUsername())
          && Integer.toString(password.hashCode()).equals(waiter.getPassword())) {
        return waiter;

      }
    }
    return null;
  }

  /**
   * Method to check user against customer login database, if found a user object is returned, else
   * null is returned.
   *
   * @param username Inputted username.
   * @param password Inputted password.
   * @return customer if correct login or null if not.
   */

  public static Customer getCustomerLogin(String username, String password) {
    for (Customer customer : database.customerLoginList()) {
      if (username.equals(customer.getUsername())
          && Integer.toString(password.hashCode()).equals(customer.getPassword())) {
        return customer;
      }
    }
    return null;
  }

  /**
   * Method to check user against kitchen staff login database, if found a user object is returned,
   * else null is returned.
   *
   * @param username Inputted username.
   * @param password Inputted password.
   * @return kitchenStaff if correct login or null if not.
   */

  public static KitchenStaff getKitchenStaffLogin(String username, String password) {
    for (KitchenStaff kitchenStaff : database.kitchenStaffLoginList()) {
      if (username.equals(kitchenStaff.getUsername())
          && Integer.toString(password.hashCode()).equals(kitchenStaff.getPassword())) {
        return kitchenStaff;
      }
    }
    return null;
  }

  /**
   * Method to check user against manager login database, if found a user object is returned, else
   * null is returned.
   *
   * @param username Inputted username.
   * @param password Inputted password.
   * @return manager if correct login or null if not.
   */

  public static Manager getManagerLogin(String username, String password) {
    for (Manager manager : database.managerLoginList()) {
      if (username.equals(manager.getUsername())
          && Integer.toString(password.hashCode()).equals(manager.getPassword())) {
        return manager;
      }
    }
    return null;
  }

}
