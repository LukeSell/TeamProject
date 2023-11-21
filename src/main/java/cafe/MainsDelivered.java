package cafe;

/**
 * Class representing order status of mains delivered.
 *
 * @author Luke Sell
 */

public class MainsDelivered implements OrderHandler {

  private static final MainsDelivered mainsDelivered = new MainsDelivered();

  private MainsDelivered() {

  }

  public static MainsDelivered getHandler() {
    return mainsDelivered;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.DESSERTSPREPARING;
  }

}
