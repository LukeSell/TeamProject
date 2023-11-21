package cafe;

/**
 * Class to represent the order status unordered.
 *
 * @author luke sell
 */

public class Unordered implements OrderHandler {

  private static final Unordered unordered = new Unordered();

  private Unordered() {

  }

  public static Unordered getHandler() {
    return unordered;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.SEATED;
  }

}
