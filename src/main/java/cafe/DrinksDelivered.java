package cafe;

/**
 * Class representing order status of drinks delivered.
 *
 * @author Luke Sell
 */
public class DrinksDelivered implements OrderHandler {

  private static final DrinksDelivered drinksDelivered = new DrinksDelivered();

  private DrinksDelivered() {

  }

  public static DrinksDelivered getHandler() {
    return drinksDelivered;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.STARTERSPREPARING;
  }

}
