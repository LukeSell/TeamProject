package cafe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The Menu and Ordering screen used by customers.
 *
 * @author Luke Sell
 * @author Michael Moorman
 * @author Liam Jones
 * 
 * @see CafeModel
 * @see DatabaseConnection
 * @see Order
 * @see User
 */

public class MenuCategoriesViewController implements ViewController {

  private CafeModel cafeModel = CafeModel.getCafeModel();
  private DatabaseConnection database = DatabaseConnection.getDatabase();
  private Order order;
  private ObservableList<TextNotification> notificationsList = FXCollections
      .observableArrayList();
  private ObservableList<Food> undoList = FXCollections.observableArrayList();
  private ObservableList<Food> currentMenu;
  private ObservableList<ObservableList<Food>> filtersList = FXCollections.observableArrayList();

  @FXML
  private TextArea foodBox;

  @FXML
  private CheckBox startersCheckBox;

  @FXML
  private CheckBox mainsCheckBox;

  @FXML
  private CheckBox dessertsCheckBox;

  @FXML
  private CheckBox drinksCheckBox;

  @FXML
  private CheckBox entireMenuCheckBox;

  @FXML
  private CheckBox lowCalorieCheckBox;

  @FXML
  private CheckBox saversMenuCheckBox;

  @FXML
  private Pagination menuView;

  @FXML
  private Pane pagePane;

  @FXML
  private TextArea showFoodBoxA;

  @FXML
  private TextArea showFoodBoxB;

  @FXML
  private TextArea showFoodBoxC;

  @FXML
  private ImageView showFoodImageA;

  @FXML
  private ImageView showFoodImageB;

  @FXML
  private ImageView showFoodImageC;

  @FXML
  private Label orderStatusLabel;

  @FXML
  private ListView<TextNotification> notificationsView;

  @FXML
  private ListView<Food> undoView;

  @FXML
  private Button homeButton;

  @FXML
  private Button resetButton;

  @FXML
  private Button exitButton;

  @FXML
  private Button waiterButton;

  @FXML
  private Button checkoutButton;

  @FXML
  private Button payButton;

  /**
   * Initialises the elements of the view.
   */

  @FXML
  private void initialize() {
    if ((order = database
        .readOrder(database.readCustomerOrderNumber(cafeModel.getUser().getHostname()))) == null) {
      order = new Order();
      order.updateTime();
      database.saveOrder(order);
      order = database.readLastOrder(order.getTime());
      database.setCustomerOrderNumber(cafeModel.getUser().getHostname(),
          order.getOrderNumber());
    } else {
      foodBox.setText(order.toString());
    }
    cafeModel.setViewController(this);
    filterDrinks();
    orderStatusLabel.setText(order.getOrderStatus().toString());
    if (!order.getOrderStatus()
        .equals(OrderStatus.SEATED)) {
      checkoutButton.setDisable(true);
    } else {
      checkoutButton.setDisable(false);
    }
    if (!order.getOrderStatus()
        .equals(OrderStatus.AWAITINGPAYMENT)) {
      payButton.setDisable(true);
    } else {
      payButton.setDisable(false);
    }
    if (order.getOrderStatus()
        .equals(OrderStatus.UNORDERED)) {
      waiterButton.setDisable(true);
    } else {
      waiterButton.setDisable(false);
    }
  }

  /**
   * Button to return to home page.
   */

  @FXML
  private void btnHome() {
    database.removeCustomer(cafeModel.getUser().getHostname());
    cafeModel.setUser(null);
    cafeModel.setViewController(null);
    database.removeOrder(order.getOrderNumber());
    NewView.load(homeButton, "/fxml/login.fxml");
  }

  /**
   * Button to exit the program.
   */

  @FXML
  private void btnExit() {
    database.removeOrder(order.getOrderNumber());
    exitButton.getScene().getWindow().hide();
  }

  /**
   * Button to place a order.
   */

  @FXML
  private void btnOrder() {
    cafeModel.updateOrder(order);
    String username = database.readWaiterHostname(Integer.toString(order.getTableNumber()));
    AlertNotification.send(username, "New Order!",
        "Table " + Integer.toString(order.getTableNumber()) + " has Ordered!");
    foodBox.setText(order.toString());
    orderStatusLabel.setText(order.getOrderStatus().toString());
  }

  /**
   * Button to refresh the elements of the view.
   */

  public void refresh() {
    order = database.readOrder(order.getOrderNumber());
    if (!order.getOrderStatus().equals(OrderStatus.SEATED)) {
      checkoutButton.setDisable(true);
    } else {
      checkoutButton.setDisable(false);
    }
    if (!order.getOrderStatus()
        .equals(OrderStatus.AWAITINGPAYMENT)) {
      payButton.setDisable(true);
    } else {
      payButton.setDisable(false);
    }
    if (order.getOrderStatus()
        .equals(OrderStatus.UNORDERED)) {
      waiterButton.setDisable(true);
    } else {
      waiterButton.setDisable(false);
    }
    orderStatusLabel.setText(order.getOrderStatus().toString());
    foodBox.setText(order.toString());

  }

  /**
   * Updates the notificatons.
   *
   * @param textNotification the text to update the notification with
   */

  public void updateNotifications(TextNotification textNotification) {
    notificationsList.add(0, textNotification);
    notificationsView.setItems(notificationsList);
  }

  /**
   * Removes the notifications.
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
   * Removes food from the order.
   */

  @FXML
  private void removeFood() {
    int i = undoView.getSelectionModel().getSelectedIndex();
    if (i >= 0) {
      Food food = undoList.remove(i);
      order.removeFood(food);
      undoView.setItems(undoList);
      database.saveOrder(order);
      foodBox.setText(order.toString());
    }
  }

  /**
   * Resets order.
   */

  @FXML
  private void btnReset() {
    order.removeAll();
    database.saveOrder(order);
    foodBox.setText(order.toString());
    undoList.clear();
    undoView.setItems(undoList);
  }

  /**
   * Button to send alert notification to waiter device.
   */

  @FXML
  private void btnCallWaiter() {
    String username = database.readWaiterHostname(Integer.toString(order.getTableNumber()));
    AlertNotification.send(username, "Waiter Needed!",
        "Table " + Integer.toString(order.getTableNumber()) + " needs Help!");
  }

  /**
   * Adds food A to order.
   */

  @FXML
  private void addFoodToOrderA() {
    Food food = currentMenu.get(menuView.getCurrentPageIndex());
    order.addFood(food);
    database.saveOrder(order);
    foodBox.setText(order.toString());
    undoList.add(0, food);
    undoView.setItems(undoList);
  }

  /**
   * Adds food B to order.
   */

  @FXML
  private void addFoodToOrderB() {
    Food food = currentMenu.get(menuView.getCurrentPageIndex() + 1);
    order.addFood(food);
    database.saveOrder(order);
    foodBox.setText(order.toString());
    undoList.add(0, food);
    undoView.setItems(undoList);
  }

  /**
   * Adds food C to order.
   */

  @FXML
  private void addFoodToOrderC() {
    Food food = currentMenu.get(menuView.getCurrentPageIndex() + 2);
    order.addFood(food);
    database.saveOrder(order);
    foodBox.setText(order.toString());
    undoList.add(0, food);
    undoView.setItems(undoList);
  }

  @FXML
  private void btnPay() {
    NewView.load(payButton, "/fxml/PaymentView.fxml");
  }

  private Node showFood(int pageIndex) {
    if (pageIndex < currentMenu.size()) {
      showFoodBoxA.setOpacity(100);
      showFoodImageA.setOpacity(100);
      showFoodBoxA.setText(currentMenu.get(pageIndex).toLongString());
      showFoodImageA.setImage(new Image(
          Main.class.getResource("/images/" + currentMenu.get(pageIndex).getURL()).toString()));
    } else {
      showFoodBoxA.setOpacity(0);
      showFoodImageA.setOpacity(0);
    }
    if ((pageIndex + 1) < currentMenu.size()) {
      showFoodBoxB.setOpacity(100);
      showFoodImageB.setOpacity(100);
      showFoodBoxB.setText(currentMenu.get(pageIndex + 1).toLongString());
      showFoodImageB.setImage(new Image(
          Main.class.getResource("/images/" + currentMenu.get(pageIndex + 1).getURL()).toString()));
    } else {
      showFoodBoxB.setOpacity(0);
      showFoodImageB.setOpacity(0);
    }
    if ((pageIndex + 2) < currentMenu.size()) {
      showFoodBoxC.setOpacity(100);
      showFoodImageC.setOpacity(100);
      showFoodBoxC.setText(currentMenu.get(pageIndex + 2).toLongString());
      showFoodImageC.setImage(new Image(
          Main.class.getResource("/images/" + currentMenu.get(pageIndex + 2).getURL()).toString()));
    } else {
      showFoodBoxC.setOpacity(0);
      showFoodImageC.setOpacity(0);
    }
    return pagePane;
  }

  /**
   * Used to move up the menu of food items.
   */

  @FXML
  private void menuUp() {
    int i = menuView.getCurrentPageIndex() - 1;
    menuView.setCurrentPageIndex((i > 0 ? i : 0));
  }

  /**
   * Used to move down the menu of food items.
   */

  @FXML
  private void menuDown() {
    int i = menuView.getCurrentPageIndex() + 1;
    menuView.setCurrentPageIndex(
        ((i + 2) < menuView.getMaxPageIndicatorCount() ? i : menuView.getMaxPageIndicatorCount()));
  }

  /**
   * Prints menu with user set filters.
   */

  private void printFilteredMenu() {
    ObservableList<Food> filteredList = filtersList.get(0);
    for (ObservableList<Food> list : filtersList) {
      ObservableList<Food> tempList = FXCollections.observableArrayList(list);
      tempList.retainAll(filteredList);
      filteredList = tempList;
    }
    filteredList.sort((foodA, foodB) -> foodA.getMeal().compareToIgnoreCase(foodB.getMeal()));
    currentMenu = filteredList;
    menuView.setMaxPageIndicatorCount(currentMenu.size());
    menuView.setPageFactory(this::showFood);
  }

  /**
   * Applies a filter to the menu for just starters.
   */

  @FXML
  private void filterStarters() {
    if (startersCheckBox.isSelected()) {
      filtersList.add(database.getMenu("starters"));
    } else {
      filtersList.remove(database.getMenu("starters"));
    }
    printFilteredMenu();
  }

  /**
   * Applies a filter to the menu for just mains.
   */

  @FXML
  private void filterMains() {
    if (mainsCheckBox.isSelected()) {
      filtersList.add(database.getMenu("mains"));
    } else {
      filtersList.remove(database.getMenu("mains"));
    }
    printFilteredMenu();
  }

  /**
   * Applies a filter to the menu for just desserts.
   */

  @FXML
  private void filterDesserts() {
    if (dessertsCheckBox.isSelected()) {
      filtersList.add(database.getMenu("desserts"));
    } else {
      filtersList.remove(database.getMenu("desserts"));
    }
    printFilteredMenu();
  }

  /**
   * Applies a filter to the menu for just drinks.
   */

  @FXML
  private void filterDrinks() {
    if (drinksCheckBox.isSelected()) {
      filtersList.add(database.getMenu("drinks"));
    } else {
      filtersList.remove(database.getMenu("drinks"));
    }
    printFilteredMenu();
  }

  @FXML
  private void filterEntireMenu() {
    if (entireMenuCheckBox.isSelected()) {
      filtersList.add(database.getMenu("entire"));
    } else {
      filtersList.remove(database.getMenu("entire"));
    }
    printFilteredMenu();
  }

  /**
   * Applies a filter to the menu for just low calorie foods.
   */

  @FXML
  private void filterLowCalorie() {
    if (lowCalorieCheckBox.isSelected()) {
      filtersList.add(database.getMenu("low_cal"));
    } else {
      filtersList.remove(database.getMenu("low_cal"));
    }
    printFilteredMenu();
  }

  /**
   * Applies a filter to the menu for just saver menu foods.
   */

  @FXML
  private void filterSaversMenu() {
    if (saversMenuCheckBox.isSelected()) {
      filtersList.add(database.getMenu("savers"));
    } else {
      filtersList.remove(database.getMenu("savers"));
    }
    printFilteredMenu();
  }
}
