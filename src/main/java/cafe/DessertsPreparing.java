package cafe;

/**
 * Class Representing the order status of desserts preparing.
 *
 * @author Luke Sell
 */
public class DessertsPreparing implements OrderHandler {

  private static final DessertsPreparing dessertsPreparing = new DessertsPreparing();

  private DessertsPreparing() {

  }

  public static DessertsPreparing getHandler() {
    return dessertsPreparing;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.DESSERTSDONE;
  }

}
