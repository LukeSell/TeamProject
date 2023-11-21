package cafe;

import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 * Class which represents the model of the backend of the system.
 * @author Luke Sell
 */

public class CafeModel {

  private InternetConnection internetConnection = InternetConnection.getConnection();
  private DatabaseConnection database = DatabaseConnection.getDatabase();
  private ObservableList<Table> tables;
  private User user;
  private ViewController currentView;
  private HashMap<String, Food> foods = new HashMap<>();
  private HashMap<Integer, Table> tablesmap = new HashMap<>();
  private static CafeModel cafeModel = new CafeModel();

  /**
   * Initialise cafe model object.
   */

  private CafeModel() {
    tables = database.tableList();
    addListToMap(database.getMenu("entire"), foods);
    addTablesToMap(tables);
  }

  /**
   * Updates notifications in the view.
   *
   * @param titleMessage title of notification
   * @param infoMessage message of notification
   */

  public void updateNotifications(String titleMessage, String infoMessage) {
    currentView.updateNotifications(new TextNotification(titleMessage, infoMessage));
    currentView.refresh();

  }

  /**
   * Update information on the order.
   * @param order specific order we want to update
   */

  public void updateOrder(Order order) {
    order.updateTime();
    order.setOrderStatus(order.getOrderStatus().next());
    DatabaseConnection.getDatabase().saveOrder(order);
  }

  /**
   * Sets the current view of the system.
   *
   * @param currentView current view
   */

  public void setViewController(ViewController currentView) {
    this.currentView = currentView;
  }

  /**
   * Method to exit program properly.
   */

  public void exit() {
    if (user != null) {
      if (user.getRole().equals(UserRole.CUSTOMER)) {
        database.removeCustomer(user.getHostname());
      } else if (user.getRole().equals(UserRole.WAITER)) {
        database.removeWaiter(user.getHostname());
      } else if (user.getRole().equals(UserRole.KITCHENSTAFF)) {
        database.removeKitchenStaff(user.getHostname());
      } else if (user.getRole().equals(UserRole.MANAGER)) {
        database.removeManager(user.getHostname());
      }
      setUser(null);
    }
  }

  /**
   * Add hashmap of food to a list.
   *
   * @param mains list of foods
   * @param food hashmap of food and their names
   */

  private void addListToMap(ObservableList<Food> mains, HashMap<String, Food> food) {
    for (int i = 0; i < mains.size(); i++) {
      foods.put(mains.get(i).getMeal(), mains.get(i));
    }
  }

  /**
   * Method to add tables list to the hashmap.
   *
   * @param tables list of tables to add
   */

  private void addTablesToMap(ObservableList<Table> tables) {
    for (Table table : tables) {
      tablesmap.put(table.getNumber(), table);
    }
  }

  /**
   * Returns hashmap of foods.
   *
   * @return hashmap of foods
   */

  public HashMap<String, Food> getFoods() {
    return foods;
  }

  /**
   * Returns hashmap of table.
   *
   * @return hashmap of table
   */

  public HashMap<Integer, Table> getTablesMap() {
    return tablesmap;
  }

  public static CafeModel getCafeModel() {
    return cafeModel;
  }

  /**
   * Returns current user using the system.
   *
   * @return user object of the user
   */

  public User getUser() {
    return user;
  }

  /**
   * Sets current user of the system.
   *
   * @param user user object to set
   */

  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Returns internet connection object made on the local system.
   *
   * @return Internet connection object.
   */

  public InternetConnection getInternetConnection() {
    return internetConnection;
  }
}
