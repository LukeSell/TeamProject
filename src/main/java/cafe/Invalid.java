package cafe;

/**
 * Class representing order status of invalid.
 *
 * @author Luke Sell
 *
 */

public class Invalid implements OrderHandler {

  private static final Invalid invalid = new Invalid();

  private Invalid() {

  }

  public static Invalid getHandler() {
    return invalid;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.INVALID;
  }

}
