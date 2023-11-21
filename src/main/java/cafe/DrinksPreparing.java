package cafe;

/**
 * Class representing order status of preparing drinks.
 *
 * @author Luke Sell
 */
public class DrinksPreparing implements OrderHandler {

  private static final DrinksPreparing drinksPreparing = new DrinksPreparing();

  private DrinksPreparing() {

  }

  public static DrinksPreparing getHandler() {
    return drinksPreparing;
  }

  public void handle() {

  }

  public OrderStatus next() {
    return OrderStatus.DRINKSDONE;
  }

}
