package cafe;

/**
 * Class to represent order status of dessert delivered.
 *
 * @author Luke Sell
 */

public class DessertsDelivered implements OrderHandler {

  private static final DessertsDelivered dessertsDelivered = new DessertsDelivered();

  private DessertsDelivered() {

  }

  public static DessertsDelivered getHandler() {
    return dessertsDelivered;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.AWAITINGPAYMENT;
  }

}
