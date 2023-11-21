package cafe;

/**
 * Enumeration that represents the different possible order statuses.
 *
 * @author yunman
 * @author Luke Sell
 */

public enum OrderStatus {

  UNORDERED("Unordered", Unordered.getHandler()),
  SEATED("Seated", Seated.getHandler()),
  ORDERED("Ordered", Ordered.getHandler()),
  CONFIRMED("Confirmed", Confirmed.getHandler()),
  STARTERSDONE("StartersDone", StartersDone.getHandler()),
  STARTERSPREPARING("StartersPreparing", StartersPreparing.getHandler()),
  STARTERSDELIVERED("StartersDelivered", StartersDelivered.getHandler()),
  MAINSDONE("MainsDone", MainsDone.getHandler()),
  MAINSPREPARING("MainsPreparing", MainsPreparing.getHandler()),
  MAINSDELIVERED("MainsDelivered", MainsDelivered.getHandler()),
  DESSERTSDONE("DessertsDone", DessertsDone.getHandler()),
  DESSERTSPREPARING("DessertsPreparing", DessertsPreparing.getHandler()),
  DESSERTSDELIVERED("DessertsDelivered", DessertsDelivered.getHandler()),
  DRINKSDONE("DrinksDone", DrinksDone.getHandler()),
  DRINKSPREPARING("DrinksPreparing", DrinksPreparing.getHandler()),
  DRINKSDELIVERED("DrinksDelivered", DrinksDelivered.getHandler()),
  AWAITINGPAYMENT("AwaitingPayment", AwaitingPayment.getHandler()),
  PAYED("Payed", Payed.getHandler()),
  INVALID("Invalid", Invalid.getHandler());

  private String description;
  private OrderHandler behaviour;

  OrderStatus(String description, OrderHandler behaviour) {
    this.description = description;
    this.behaviour = behaviour;
  }

  public static OrderStatus fromString(String string) {
    for (OrderStatus orderStatus : OrderStatus.values()) {
      if (orderStatus.toString().equals(string)) {
        return orderStatus;
      }
    }
    return OrderStatus.INVALID;
  }

  public void handle() {
    behaviour.handle();
  }

  public OrderStatus next() {
    return behaviour.next();
  }

  @Override
  public String toString() {
    return description;
  }
}
