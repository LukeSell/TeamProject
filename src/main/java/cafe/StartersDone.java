package cafe;

/**
 * Class to represent the order status starters done.
 *
 * @author luke sell
 */

public class StartersDone implements OrderHandler {

  private static final StartersDone startersDone = new StartersDone();

  private StartersDone() {

  }

  public static StartersDone getHandler() {
    return startersDone;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.STARTERSDELIVERED;
  }

}
