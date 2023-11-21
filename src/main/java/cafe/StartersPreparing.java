package cafe;

/**
 * Class to represent the order status starters being prepared.
 *
 * @author luke sell
 */

public class StartersPreparing implements OrderHandler {

  private static final StartersPreparing startersPreparing = new StartersPreparing();

  private StartersPreparing() {

  }

  public static StartersPreparing getHandler() {
    return startersPreparing;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.STARTERSDONE;
  }

}
