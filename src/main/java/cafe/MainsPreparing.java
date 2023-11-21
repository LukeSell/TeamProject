package cafe;

/**
 * Class representing order status of mains preparing.
 *
 * @author Luke Sell
 */

public class MainsPreparing implements OrderHandler {

  private static final MainsPreparing mainsPreparing = new MainsPreparing();

  private MainsPreparing() {

  }

  public static MainsPreparing getHandler() {
    return mainsPreparing;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.MAINSDONE;
  }

}
