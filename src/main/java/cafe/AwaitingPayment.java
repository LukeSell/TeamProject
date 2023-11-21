package cafe;

/**
 * Class that represents that order status of an awaiting payment.
 *
 * @author Luke Sell
 */
public class AwaitingPayment implements OrderHandler {

  private static final AwaitingPayment awaitingPayment = new AwaitingPayment();

  private AwaitingPayment() {
  }

  public static AwaitingPayment getHandler() {
    return awaitingPayment;
  }

  public void handle() {
  }

  public OrderStatus next() {
    return OrderStatus.PAYED;
  }
}
