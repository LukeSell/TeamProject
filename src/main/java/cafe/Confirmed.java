package cafe;

/**
 * Class that represents that order status of confirmed.
 *
 * @author Luke Sell
 */

public class Confirmed implements OrderHandler {

  private static final Confirmed confirmed = new Confirmed();

  private Confirmed() {

  }

  public static Confirmed getHandler() {
    return confirmed;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.DRINKSPREPARING;
  }

}
