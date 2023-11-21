package cafe;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Object representing whole kitchen.
 *
 * @author Yunman
 * @author Luke Sell
 */

public class Kitchen {

  private CafeModel cafeModel = CafeModel.getCafeModel();
  private DatabaseConnection database = DatabaseConnection.getDatabase();
  private double drinkTime = 0;
  private double starterTime = 0;
  private double mainTime = 0;
  private double dessertTime = 0;
  private Timer timer;
  private Order order;

  /**
   * Method to change order status to being prepared.
   *
   * @param order order to change status
   */

  public void prepareCourse(Order order) {
    String username = "";
    String usernameC = "";
    switch (order.getOrderStatus()) {
      case CONFIRMED:
        if (!order.getDrinks().isEmpty()) {
          for (Food food : order.getDrinks()) {
            drinkTime = (food.getTime() > drinkTime ? food.getTime() : drinkTime);
          }
          kitchenTimer(drinkTime);
          cafeModel.updateOrder(order);
          username = database.readWaiterHostname(Integer.toString(order.getTableNumber()));
          AlertNotification.send(username, "Food is being Prepared!",
              "Customer Drinks are being Prepared!");
          usernameC = database
              .readCustomerHostname(Integer.toString(order.getTableNumber()));
          AlertNotification.send(usernameC, "Food is being Prepared!",
              "Customer Drinks are being Prepared!");
          break;
        }
        order.setOrderStatus(OrderStatus.DRINKSDELIVERED);
        database.saveOrder(order);
      case DRINKSDELIVERED:
        if (!order.getStarters().isEmpty()) {
          for (Food food : order.getStarters()) {
            starterTime = (food.getTime() > starterTime ? food.getTime() : starterTime);
          }
          kitchenTimer(starterTime);
          cafeModel.updateOrder(order);
          username = database.readWaiterHostname(Integer.toString(order.getTableNumber()));
          AlertNotification.send(username, "Food is being Prepared!",
              "Customer Starters are being Prepared!");
          usernameC = database
              .readCustomerHostname(Integer.toString(order.getTableNumber()));
          AlertNotification.send(usernameC, "Food is being Prepared!",
              "Customer Starters are being Prepared!");
          break;
        }
        order.setOrderStatus(OrderStatus.STARTERSDELIVERED);
        database.saveOrder(order);
      case STARTERSDELIVERED:
        if (!order.getMains().isEmpty()) {
          for (Food food : order.getMains()) {
            mainTime = (food.getTime() > mainTime ? food.getTime() : mainTime);
          }
          kitchenTimer(mainTime);
          cafeModel.updateOrder(order);
          username = database.readWaiterHostname(Integer.toString(order.getTableNumber()));
          AlertNotification.send(username, "Food is being Prepared!",
              "Customer Mains are being Prepared!");
          usernameC = database
              .readCustomerHostname(Integer.toString(order.getTableNumber()));
          AlertNotification.send(usernameC, "Food is being Prepared!",
              "Customer Mains are being Prepared!");
          break;
        }
        order.setOrderStatus(OrderStatus.MAINSDELIVERED);
        database.saveOrder(order);
      case MAINSDELIVERED:
        if (!order.getDesserts().isEmpty()) {
          for (Food food : order.getDesserts()) {
            dessertTime = (food.getTime() > dessertTime ? food.getTime() : dessertTime);
          }
          kitchenTimer(dessertTime);
          cafeModel.updateOrder(order);

          username = database.readWaiterHostname(Integer.toString(order.getTableNumber()));
          AlertNotification.send(username, "Food is being Prepared!",
              "Customer Desserts are being Prepared!");
          usernameC = database
              .readCustomerHostname(Integer.toString(order.getTableNumber()));
          AlertNotification.send(usernameC, "Food is being Prepared!",
              "Customer Desserts are being Prepared!");
          break;
        }
        break;
      default:
        break;
    }
  }

  /**
   * Method to change order status to being delivered.
   *
   * @param order order to change status
   */

  public void deliverCourse(Order order) {
    String username = "";
    String usernameC = "";
    switch (order.getOrderStatus()) {
      case DRINKSPREPARING:
        timer.cancel();
        cafeModel.updateOrder(order);
        username = database.readWaiterHostname(Integer.toString(order.getTableNumber()));
        AlertNotification.send(username, "Food Ready!",
            "Customer Drinks are ready to be delivered!");
        usernameC = database
            .readCustomerHostname(Integer.toString(order.getTableNumber()));
        AlertNotification.send(usernameC, "Food Ready!",
            "Customer Drinks are ready to be delivered!");
        break;
      case STARTERSPREPARING:
        timer.cancel();
        cafeModel.updateOrder(order);
        username = database.readWaiterHostname(Integer.toString(order.getTableNumber()));
        AlertNotification.send(username, "Food Ready!",
            "Customer Starters are ready to be delivered!");
        usernameC = database
            .readCustomerHostname(Integer.toString(order.getTableNumber()));
        AlertNotification.send(usernameC, "Food Ready!",
            "Customer Starters are ready to be delivered!");
        break;
      case MAINSPREPARING:
        timer.cancel();
        cafeModel.updateOrder(order);
        username = database.readWaiterHostname(Integer.toString(order.getTableNumber()));
        AlertNotification.send(username, "Food Ready!",
            "Customer Mains are ready to be delivered!");
        usernameC = database
            .readCustomerHostname(Integer.toString(order.getTableNumber()));
        AlertNotification.send(usernameC, "Food Ready!",
            "Customer Mains are ready to be delivered!");
        break;
      case DESSERTSPREPARING:
        timer.cancel();
        cafeModel.updateOrder(order);
        username = database.readWaiterHostname(Integer.toString(order.getTableNumber()));
        AlertNotification.send(username, "Food Ready!",
            "Customer Desserts are ready to be delivered!");
        usernameC = database
            .readCustomerHostname(Integer.toString(order.getTableNumber()));
        AlertNotification.send(usernameC, "Food Ready!",
            "Customer Desserts are ready to be delivered!");
        break;
      default:
        break;
    }
  }

  /**
   * In system timer of the kitchen.
   *
   * @param minutes double variable to store in minutes.
   */

  public void kitchenTimer(double minutes) {
    int time = (int) (minutes * 1000 * 60);
    timer = new Timer();
    timer.schedule(new Task(), time);
  }

  /**
   * Runs a task.
   */
  class Task extends TimerTask {

    /**
     * Alerts the user that their food has been delayed.
     */
    public void run() {
      String username = database.readWaiterHostname(Integer.toString(order.getTableNumber()));
      AlertNotification.send(username, "Food Delayed!", "The course has not yet been cooked!");
      timer.cancel();
    }
  }
}