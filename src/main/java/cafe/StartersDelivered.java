package cafe;

/**
 * Class to represent the order status starters delivered.
 *
 * @author luke sell
 */

public class StartersDelivered implements OrderHandler {

  private static final StartersDelivered startersDelivered = new StartersDelivered();

  private StartersDelivered() {

  }

  public static StartersDelivered getHandler() {
    return startersDelivered;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.MAINSPREPARING;
  }

}
