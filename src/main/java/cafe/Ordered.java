package cafe;

/**
 * Class to represent the order status ordered.
 *
 * @author luke sell
 */

public class Ordered implements OrderHandler {

  private static final Ordered ordered = new Ordered();

  private Ordered() {

  }

  public static Ordered getHandler() {
    return ordered;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.CONFIRMED;
  }

}
