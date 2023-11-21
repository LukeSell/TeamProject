package cafe;

/**
 * This class represents the customer object and inherits from the User object.
 *
 * @author thanushen
 */

public class Customer extends User {

  private Table tableSeated;
  private Order order;
  private int orderNumber;
  private int tableNumber;
  private int orderStatus;

  /**
   * Setting Up a customer object with the parameters bellow.
   *
   * @param username username of the customer
   * @param password password of the customer
   * @param hostname hostname of the customer
   */
  public Customer(String username, String password, String hostname) {
    super(username, password, hostname, UserRole.CUSTOMER);
  }

  /**
   * Setting Up a customer object with the parameters bellow.
   *
   * @param hostname hostname of the customer
   */
  public Customer(String hostname) {
    super(hostname, UserRole.CUSTOMER);
  }

  /**
   * Setting Up a customer object with the parameters bellow.
   *
   * @param username username of the customer
   * @param hostname hostname of the customer
   */
  public Customer(String username, String hostname) {
    super(username, hostname, UserRole.CUSTOMER);
  }

  /**
   * Setting up a customer object with the parameters bellow.
   *
   * @param username username of the customer
   * @param password password of the customer
   * @param hostname hostname of the customer
   * @param tableNumber table number assigned to customer
   * @param orderNumber order number assigned to customer
   */
  public Customer(String username, String password, String hostname, int tableNumber,
      int orderNumber) {
    super(username, password, hostname, UserRole.CUSTOMER);
    this.tableNumber = tableNumber;
    this.orderNumber = orderNumber;
  }

  /**
   * Gets the current table number of the customer.
   *
   * @return table number of customer.
   */
  public int getTableNumber() {
    return tableNumber;
  }

  /**
   * Gets order number of the customer.
   * @return orderNumber.
   */
  
  public int getOrderNumber() {
    return orderNumber;
  }

  /**
   * Gets table object where customer is seated.
   * @return tableSeatet table the customer is seated.
   */
  
  public Table getTableSeated() {
    return tableSeated;
  }

  /**
   * assigns the customer a table.
   *
   * @param tableSeated table to assign to customer
   */
  
  public void setTableSeated(Table tableSeated) {
    this.tableSeated = tableSeated;
  }

  /**
   * gets the order of the customer.
   *
   * @return order object of the customer
   */
  
  public Order getOrder() {
    return order;
  }

  /**
   * Assigns an order to the customer.
   *
   * @param order order we want to assign customer
   */
  
  public void setOrder(Order order) {
    this.order = order;
  }

  /**
   * gets the current order status of the customers order.
   *
   * @return customers order status
   */
  public int getOrderStatus() {
    return orderStatus;
  }

  /**
   * Sets the current order status of a customer order.
   *
   * @param orderStatus Order status of a customers order
   */
  public void setOrderStatus(int orderStatus) {
    this.orderStatus = orderStatus;
  }
}
