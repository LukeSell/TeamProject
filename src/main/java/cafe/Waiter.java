package cafe;

/**
 * Object that holds waiter information.
 *
 * @author thanushenbalaskandar
 */
public class Waiter extends User {

  private Table tableAssigned;
  private int tableNumber;
  private Order order;
  private int orderStatus;


  /**
   * Initialises/creates a new waiter.
   *
   * @param username for the database
   * @param password for the database
   * @param hostname the the database
   * @param tableAssigned the current table to assign to this waiter.
   * @param tableNumber table number
   */
  public Waiter(String username, String password, String hostname, Table tableAssigned,
      int tableNumber) {
    super(username, password, hostname, UserRole.WAITER);
    this.tableAssigned = tableAssigned;
    this.tableNumber = tableNumber;
  }

  /**
   * Initialises/creates a new waiter.
   *
   * @param username for the database
   * @param password for the database
   * @param hostname the the database
   */
  public Waiter(String username, String password, String hostname) {
    super(username, password, hostname, UserRole.WAITER);
  }

  /**
   * Initialises/creates a new waiter.
   *
   * @param hostname the the database
   */
  public Waiter(String hostname) {
    super(hostname, UserRole.WAITER);
  }

  /**
   * Initialises/creates a new waiter.
   *
   * @param username for the database
   * @param hostname the the database
   */
  public Waiter(String username, String hostname) {
    super(username, hostname, UserRole.WAITER);
  }

  /**
   * Returns table number of table assigned.
   *
   * @return table number
   */

  public int getTableNumber() {
    return tableNumber;
  }

  /**
   * Sets the assigned table.
   *
   * @param tableAssigned the table to set the current assigned table to.
   */
  public void setTableAssigned(Table tableAssigned) {
    this.tableAssigned = tableAssigned;
  }

  /**
   * Obtains the assigned table.
   *
   * @return the assigned table
   */
  public Table getTableAssigned() {
    return tableAssigned;
  }

  /**
   * Sets the order.
   *
   * @param order the order to set the current order to
   */
  public void setOrder(Order order) {
    this.order = order;
  }

  /**
   * Obtains the order.
   *
   * @return the order
   */
  public Order getOrder() {
    return order;
  }

  /**
   * Sets waiter objects order status to the order status of the order.
   *
   * @param order is the current status of the order
   */
  public void setOrderStatus(int order) {
    orderStatus = order;
  }

  /**
   * Obtains the order status.
   *
   * @return the order status in the form of an int
   */
  public int getOrderStatus() {
    return orderStatus;
  }

}
