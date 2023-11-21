package cafe;

/**
 * Class to represent the order status of Desserts Done.
 *
 * @author Luke Sell
 */
public class DessertsDone implements OrderHandler {

  private static final DessertsDone dessertsDone = new DessertsDone();

  private DessertsDone() {

  }

  public static DessertsDone getHandler() {
    return dessertsDone;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.DESSERTSDELIVERED;
  }

}
