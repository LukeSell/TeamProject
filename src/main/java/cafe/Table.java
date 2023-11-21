package cafe;

/**
 * A table for the customers to be seated at.
 *
 * @author Luke Sell
 * 
 * @see User
 * @see Order
 * @see TableStatus
 */

public class Table {

  private final int number;
  private final int seats;
  private User waiter = null;
  private User customer = null;
  private Order order = null;
  private TableStatus status = TableStatus.EMPTY;

  public Table(int number, int seats) {
    this.number = number;
    this.seats = seats;
  }

  public int getNumber() {
    return number;
  }

  public int getSeats() {
    return seats;
  }

  public User getWaiter() {
    return waiter;
  }

  public User getCustomer() {
    return customer;
  }

  public TableStatus getStatus() {
    return status;
  }

  public void setWaiter(User waiter) {
    this.waiter = waiter;
  }

  public void setCustomer(User customer) {
    this.customer = customer;
  }

  public void setStatus(TableStatus status) {
    this.status = status;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  /**
   * Checks if a group of customers can all be seated at this table.
   *
   * @param customers is the amount of customers.
   * @return true if the customers can be seated at this table, else false.
   */

  public boolean hasSeats(int customers) {
    return (this.seats >= customers) && status == TableStatus.EMPTY ? true : false;
  }

  /**
   * Returns a string representation of the table object so that it can be displayed.
   * @return string of table information
   */

  @Override
  public String toString() {
    if (waiter == null) {
      return "Table Number: " + number + " Table Seats: " + seats + "&" + "Table Waiter: None"
          + " Table Status: " + status.toString();
    }
    return "Table Number: " + number + " Table Seats: " + seats + "&" + "Table Waiter: "
        + waiter.getUsername() + " Table Status: " + status.toString();
  }
}
