package cafe;

/**
 * Class representing order status of mains done.
 *
 * @author Luke Sell
 */

public class MainsDone implements OrderHandler {

  private static final MainsDone mainsDone = new MainsDone();

  private MainsDone() {

  }

  public static MainsDone getHandler() {
    return mainsDone;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.MAINSDELIVERED;
  }

}
