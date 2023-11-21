package cafe;

/**
 * Class representing order status of drinks Done.
 *
 * @author Luke Sell
 */
public class DrinksDone implements OrderHandler {

  private static final DrinksDone drinksDone = new DrinksDone();

  private DrinksDone() {

  }

  public static DrinksDone getHandler() {
    return drinksDone;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.DRINKSDELIVERED;
  }

}
