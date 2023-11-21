package cafe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * An object representing a customers order, with courses containing a list of
 * food objects that have been added to the order.
 *
 * @author Yunman
 * @author Luke Sell
 */

public class Order {

  private ObservableList<Food> starters = FXCollections.observableArrayList();
  private ObservableList<Food> mains = FXCollections.observableArrayList();
  private ObservableList<Food> desserts = FXCollections.observableArrayList();
  private ObservableList<Food> drinks = FXCollections.observableArrayList();
  private Table table;
  private OrderStatus orderstatus = OrderStatus.UNORDERED;
  private int orderNumber;
  private String orderPlaced;
  private double totalCost = 0;

  public Order() {
    
  }

  /**
   * Initialises order object.
   *
   * @param orderNumber order number
   * @param tableNumber table number
   * @param starters    list of starters
   * @param mains       list of mains
   * @param desserts    list of desserts
   * @param drinks      list of drinks
   * @param totalCost   total cost of all food items in order
   * @param orderPlaced when the order was placed
   * @param orderStatus status of order
   */

  public Order(int orderNumber, int tableNumber, String starters, String mains, String desserts,
      String drinks, double totalCost, String orderPlaced, String orderStatus) {
    this.orderNumber = orderNumber;
    this.table = CafeModel.getCafeModel().getTablesMap().get(tableNumber);
    if (!starters.equals("None")) {
      for (String food : starters.split("~")) {
        addFood(CafeModel.getCafeModel().getFoods().get(food));
      }
    }
    if (!mains.equals("None")) {
      for (String food : mains.split("~")) {
        addFood(CafeModel.getCafeModel().getFoods().get(food));
      }
    }
    if (!desserts.equals("None")) {
      for (String food : desserts.split("~")) {
        addFood(CafeModel.getCafeModel().getFoods().get(food));
      }
    }
    if (!drinks.equals("None")) {
      for (String food : drinks.split("~")) {
        addFood(CafeModel.getCafeModel().getFoods().get(food));
      }
    }
    this.totalCost = totalCost;
    this.orderPlaced = orderPlaced;
    this.orderstatus = OrderStatus.fromString(orderStatus);
  }

  /**
   * Returns order number.
   * 
   * @return orderNumber ordernumber of order
   */

  public int getOrderNumber() {
    return orderNumber;
  }

  /**
   * Sets order number.
   * 
   * @param orderNumber order number of an order
   */

  public void setOrderNumber(int orderNumber) {
    this.orderNumber = orderNumber;
  }

  /**
   * Sets table to order.
   * 
   * @param table table we want to set.
   */

  public void setTable(Table table) {
    this.table = table;
  }

  /**
   * gets total cost of all items in the order.
   *
   * @return the total cost
   */

  public double getTotalCost() {
    return totalCost;
  }

  /**
   * Sets the total cost of the order.
   *
   * @param totalCost total cost
   */

  public void setTotalCost(double totalCost) {
    this.totalCost = totalCost;
  }

  /**
   * Updates time from system time.
   */

  public void updateTime() {
    orderPlaced = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
  }

  /**
   * Returns time when order was placed.
   *
   * @return order placed
   */

  public String getTime() {
    return orderPlaced;
  }

  /**
   * Returns list of starter foods.
   *
   * @return list of starters food
   */

  public ObservableList<Food> getStarters() {
    return starters;
  }

  /**
   * Returns list of main foods.
   *
   * @return list of main foods
   */

  public ObservableList<Food> getMains() {
    return mains;
  }

  /**
   * Returns list of dessert foods.
   *
   * @return list of dessert foods
   */

  public ObservableList<Food> getDesserts() {
    return desserts;
  }

  /**
   * Returns list of drinks foods.
   *
   * @return list of drinks foods
   */

  public ObservableList<Food> getDrinks() {
    return drinks;
  }

  /**
   * Gets order table number.
   *
   * @return table number
   */

  public int getTableNumber() {
    if (table != null) {
      return table.getNumber();
    }
    return -1;
  }

  /**
   * Adds food to the order.
   *
   * @param food food object to be added
   */

  public void addFood(Food food) {
    switch (food.getMealType()) {
      case STARTER:
        starters.add(food);
        starters.sort((foodA, foodB) -> foodA.getMeal().compareToIgnoreCase(foodB.getMeal()));
        totalCost += food.getPrice();
        break;
      case MAIN:
        mains.add(food);
        mains.sort((foodA, foodB) -> foodA.getMeal().compareToIgnoreCase(foodB.getMeal()));
        totalCost += food.getPrice();
        break;
      case DESSERT:
        desserts.add(food);
        desserts.sort((foodA, foodB) -> foodA.getMeal().compareToIgnoreCase(foodB.getMeal()));
        totalCost += food.getPrice();
        break;
      case DRINK:
        drinks.add(food);
        drinks.sort((foodA, foodB) -> foodA.getMeal().compareToIgnoreCase(foodB.getMeal()));
        totalCost += food.getPrice();
        break;
      default:
        throw new IllegalArgumentException();
    }
  }

  /**
   * Removes food to the order.
   *
   * @param food food object to be removed
   */

  public void removeFood(Food food) {
    totalCost -= food.getPrice();
    switch (food.getMealType()) {
      case STARTER:
        starters.remove(food);
        break;
      case MAIN:
        mains.remove(food);
        break;
      case DESSERT:
        desserts.remove(food);
        break;
      case DRINK:
        drinks.remove(food);
        break;
      default:
        break;
    }
  }

  /**
   * Adds starter to the order.
   *
   * @param food food object to be added
   */

  public void addStarter(Food food) { // remove add starters
    starters.add(food);
    totalCost += food.getPrice();
  }

  /**
   * Adds main to the order.
   *
   * @param food food object to be added
   */

  public void addMain(Food food) {
    mains.add(food);
    totalCost += food.getPrice();
  }

  /**
   * Adds dessert to the order.
   *
   * @param food food object to be added
   */

  public void addDessert(Food food) {
    desserts.add(food);
    totalCost += food.getPrice();
  }

  /**
   * Adds drink to the order.
   *
   * @param food food object to be added
   */

  public void addDrink(Food food) {
    drinks.add(food);
    totalCost += food.getPrice();
  }

  /**
   * Removes starter from the list.
   */

  public void removeStarter() {
    if (!starters.isEmpty()) {
      Food food = starters.remove(starters.size() - 1);
      totalCost -= food.getPrice();
    }
  }

  /**
   * Removes main from the list.
   */

  public void removeMain() {
    if (!mains.isEmpty()) {
      Food food = mains.remove(mains.size() - 1);
      totalCost -= food.getPrice();
    }
  }

  /**
   * Removes dessert from the list.
   */

  public void removeDessert() {
    if (!desserts.isEmpty()) {
      Food food = desserts.remove(desserts.size() - 1);
      totalCost -= food.getPrice();
    }
  }

  /**
   * Removes drink from the list.
   */

  public void removeDrink() {
    if (!drinks.isEmpty()) {
      Food food = drinks.remove(drinks.size() - 1);
      totalCost -= food.getPrice();
    }
  }

  /**
   * Removes all foods from the order.
   */

  public void removeAll() {
    starters.clear();
    mains.clear();
    desserts.clear();
    drinks.clear();
    totalCost = 0;
  }

  /**
   * Gets status of order.
   *
   * @return order status
   */

  public OrderStatus getOrderStatus() {
    return orderstatus;
  }

  /**
   * Sets order status.
   * 
   * @param orderstatus status of an order
   */

  public void setOrderStatus(OrderStatus orderstatus) {
    this.orderstatus = orderstatus;
  }

  /**
   * Returns a string containing full order information.
   *
   * @return order to string
   */

  @Override
  public String toString() {
    return "Order Number: " + orderNumber + "\nTable: "
        + ((getTableNumber() == -1) ? "Not Seated" : getTableNumber()) + "\nStarters: "
        + starters.toString().replace("[", "").replace("]", "") + (starters.isEmpty() ? "None" : "")
        + "\nMains: " + mains.toString().replace("[", "").replace("]", "")
        + (mains.isEmpty() ? "None" : "") + "\nDesserts: "
        + desserts.toString().replace("[", "").replace("]", "") + (desserts.isEmpty() ? "None" : "")
        + "\nDrinks: " + drinks.toString().replace("[", "").replace("]", "")
        + (drinks.isEmpty() ? "None" : "") + "\nTotal Cost: £" + totalCost + "0\nLast Update: "
        + orderPlaced + "\nOrder Status: " + orderstatus;
  }
}
