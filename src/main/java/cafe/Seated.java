package cafe;

/**
 * Class to represent the order status seated.
 *
 * @author luke sell
 */

public class Seated implements OrderHandler {

  private static final Seated seated = new Seated();

  private Seated() {

  }

  public static Seated getHandler() {
    return seated;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.ORDERED;
  }

}
