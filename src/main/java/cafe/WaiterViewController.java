package cafe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * The controller for the waiter view of the program, meaning the area where the waiter is able to
 * confirm a customer order, assign themselves to a customer/table, cancel an order, or view a
 * tables current order.
 *
 * @author Luke Sell
 * @author Liam Jones
 */

public class WaiterViewController implements ViewController {

  private CafeModel cafeModel = CafeModel.getCafeModel();
  private DatabaseConnection database = DatabaseConnection.getDatabase();
  private ObservableList<TextNotification> notificationsList = FXCollections.observableArrayList();
  private Order order;

  @FXML
  private Button homeButton;

  @FXML
  private Button refreshButton;

  @FXML
  private Button exitButton;

  @FXML
  private Button selectTableButton;

  @FXML
  private Button finishButton;

  @FXML
  private Button orderDeliveredButton;

  @FXML
  private ListView<TextNotification> notificationsView;

  @FXML
  private Button confirmOrderButton;

  @FXML
  private TextArea orderInfo;

  @FXML
  private ChoiceBox<Customer> customerButton;

  @FXML
  private ChoiceBox<Waiter> waiterButton;

  @FXML
  private ChoiceBox<Table> tablesButton;

  @FXML
  private Button assignButton;

  @FXML
  private Button cancelButton;

  @FXML
  private ChoiceBox<Table> tableList;
  private static int currentTableNum = 0;

  /**
   * Initialises the 'cafe model', and initialises each of the buttons for assigning the customer to
   * a table, waiter to a table, etc.
   */
  
  @FXML
  private void initialize() {
    cafeModel.setViewController(this);
    customerButton.setItems(database.customerList());
    waiterButton.setItems(database.waiterList());
    tablesButton.setItems(database.tableList());
    tableList.setItems(database.tableList());
  }

  /**
   * Obtains the current table number.
   *
   * @return the current table number as an int
   */
  public static int getCurrentTableNum() {
    return currentTableNum;
  }

  /**
   * Refreshes the lists.
   */
  public void refresh() {
    initialize();

  }

  @FXML
  private void btnRefresh() {
    initialize();

  }

  /**
   * Updates the notification of the customer/table needing assistance.
   */
  
  public void updateNotifications(TextNotification textNotification) {
    notificationsList.add(0, textNotification);
    notificationsView.setItems(notificationsList);
  }

  /**
   * Removes the notification of a customer/table needing assistance.
   */
  
  @FXML
  private void removeNotification() {
    int i = notificationsView.getSelectionModel().getSelectedIndex();
    if (i >= 0) {
      notificationsList.remove(i);
      notificationsView.setItems(notificationsList);
    }
  }

  /**
   * Enables the functionality of the Home button so that it enables the user to go back to the main
   * screen.
   */
  
  @FXML
  private void btnHome() {
    database.removeWaiter(cafeModel.getUser().getHostname());
    cafeModel.setUser(null);
    cafeModel.setViewController(null);
    NewView.load(homeButton, "/fxml/login.fxml");
  }

  /**
   * Hides the current scene.
   */
  
  @FXML
  void btnExit() {
    exitButton.getScene().getWindow().hide();
  }

  @FXML
  private void btnFinishOrder() {
    String username =
        database.readCustomerHostname(Integer.toString(order.getTableNumber()));
    AlertNotification.send(username, "Payment Accepted!",
        "You may now Leave!");
    database.removeOrder(order.getOrderNumber());

  }

  /**
   * Assigns the values in the dropdown boxes accordingly (Customer -> table, waiter -> table).
   */
  
  @FXML
  void btnAssign() {
    if ((customerButton.getValue() != null) && (waiterButton.getValue() != null)
        && (tablesButton.getValue() != null)) {
      order = database
          .readOrder(database.readCustomerOrderNumber(customerButton.getValue().getHostname()));
      if (order.getOrderStatus().equals(OrderStatus.UNORDERED)) {
        database.setCustomerTableNumber(customerButton.getValue().getHostname(),
            tablesButton.getValue().getNumber());
        database.setWaiterTableNumber(waiterButton.getValue().getHostname(),
            tablesButton.getValue().getNumber());
        database.setTableCustomerWaiter(customerButton.getValue().getHostname(),
            waiterButton.getValue().getHostname(),
            Integer.toString(tablesButton.getValue().getNumber()));
        order.setTable(tablesButton.getValue());
        cafeModel.updateOrder(order);
        orderInfo.setText("Assigned!");
        String username =
            database.readCustomerHostname(Integer.toString(order.getTableNumber()));
        AlertNotification.send(username, "Waiter Assigned!",
            "You may now Order!");
      } else {
        orderInfo.setText("Customer already Assigned!");
      }
    }
  }

  /**
   * Allows the waiter to serve the selected table.
   */
  
  @FXML
  private void btnSelectTable() {
    if (tableList.getValue() != null) {
      order = database.readOrder(database.readCustomerOrderNumber(
          database.readCustomerHostname(Integer.toString(tableList.getValue().getNumber()))));
      if (order != null) {
        orderInfo.setText(order.toString());
      } else {
        orderInfo.setText("Customer has not yet Ordered!");
      }
      refresh();
    }
  }

  /**
   * Allows the waiter to mark a specific tables order as delivered.
   */
  
  @FXML
  private void btnOrderDelivered() {
    if (order.getOrderStatus().equals(OrderStatus.DRINKSDONE)
        || order.getOrderStatus().equals(OrderStatus.STARTERSDONE)
        || order.getOrderStatus().equals(OrderStatus.MAINSDONE)
        || order.getOrderStatus().equals(OrderStatus.DESSERTSDONE)) {
      cafeModel.updateOrder(order);
      orderInfo.setText("Course delivered!");
    } else {
      orderInfo.setText("Course already delivered!");
    }
  }

  /**
   * Cancels the order/all orders for the currently chosen table.
   */
  
  @FXML
  void btnCancelOrder() {
    if (DatabaseConnection.getDatabase().removeOrder(order.getOrderNumber())) {
      orderInfo.setText("Order has been removed " + "\n" + "successfully.");
    } else {
      orderInfo
          .setText("Unable to remove order!" + "\n" + "Maybe it was already" + "\n" + "removed?");
    }
  }

  /**
   * Allows the waiter to take a tables order.
   */
  
  @FXML
  private void btnConfirmOrder() {
    if (order.getOrderStatus().equals(OrderStatus.ORDERED)) {
      cafeModel.updateOrder(order);
      String username = database.readKitchenStaffHostname(Integer.toString(order.getTableNumber()));
      AlertNotification.send(username, "Order Confirmed!",
          "Table " + Integer.toString(order.getTableNumber()) + " has Ordered!");
      orderInfo.setText("Order confirmed!");
    } else {
      orderInfo.setText("Order already confirmed!");
    }
  }
}
