package cafe;

/**
 * Initialises a Users Role value, along with its description.
 *
 * @author Thanushen Balaskandar.
 */
public enum UserRole {

  CUSTOMER("Customer"),
  WAITER("Waiter"),
  KITCHENSTAFF("KitchenStaff"),
  MANAGER("Manager");

  private String description;

  /**
   * Initialises a new UserRole with a specified description.
   *
   * @param description the description for the users role.
   */
  UserRole(String description) {
    this.description = description;
  }

  /**
   * Returns the description (a string).
   */
  @Override
  public String toString() {
    return description;
  }
}
