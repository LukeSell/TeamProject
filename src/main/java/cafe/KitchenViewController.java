package cafe;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller for the kitchen view.
 */

public class KitchenViewController implements ViewController {

  private CafeModel cafeModel = CafeModel.getCafeModel();
  private DatabaseConnection database = DatabaseConnection.getDatabase();
  private Kitchen kitchen = new Kitchen();
  private ObservableList<TextNotification> notificationsList = FXCollections.observableArrayList();

  @FXML
  private Button homeButton;

  @FXML
  private Button exitButton;

  @FXML
  private Button refreshButton;

  @FXML
  private ListView<TextNotification> notificationsView;

  @FXML
  private TableView<Order> orderTable;

  @FXML
  private TableColumn<Order, Integer> orderNumberColumn;

  @FXML
  private TableColumn<Order, Integer> tableNumberColumn;

  @FXML
  private TableColumn<Order, String> startersColumn;

  @FXML
  private TableColumn<Order, String> mainsColumn;

  @FXML
  private TableColumn<Order, String> dessertsColumn;

  @FXML
  private TableColumn<Order, String> drinksColumn;

  @FXML
  private TableColumn<Order, String> costColumn;

  @FXML
  private TableColumn<Order, String> timeColumn;

  @FXML
  private TableColumn<Order, String> statusColumn;

  @FXML
  private ChoiceBox<Order> orderButton;

  @FXML
  private Button deliverCourseButton;

  @FXML
  private ChoiceBox<Order> ordersButton;

  @FXML
  private Button prepareCourseButton;

  /**
   * Button mark to the course is ready to be delivered.
   */

  @FXML
  void btnDeliverCourse() {
    if (orderButton.getValue() != null) {
      kitchen.deliverCourse(orderButton.getValue());
      initialize();
    }
  }

  /**
   * Button mark the course is being prepared.
   */

  @FXML
  void btnPrepareCourse() {
    if (ordersButton.getValue() != null) {
      kitchen.prepareCourse(ordersButton.getValue());
      initialize();
    }
  }

  /**
   * Button to exit the program.
   */

  @FXML
  void btnExit() {
    exitButton.getScene().getWindow().hide();
  }

  /**
   * Button to refresh the elements of the view.
   */

  @FXML
  void btnRefresh() {
    initialize();
  }

  /**
   * Method to refresh the elements of the view.
   */

  public void refresh() {
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
   * Method to remove notifications.
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
   * Initialise the elements of the view.
   */

  @FXML
  private void initialize() {
    cafeModel.setViewController(this);
    orderButton.setItems(database.orderList());
    ordersButton.setItems(database.orderList());
    orderTable.setItems(database.orderList());
    orderNumberColumn.setCellValueFactory(
        string -> new SimpleIntegerProperty(string.getValue().getOrderNumber()).asObject());
    tableNumberColumn.setCellValueFactory(
        string -> new SimpleIntegerProperty(string.getValue().getTableNumber()).asObject());
    startersColumn.setCellValueFactory(string -> new SimpleStringProperty(
        string.getValue().getStarters().toString().replace("[", "").replace("]", "")
            + (string.getValue().getStarters().isEmpty() ? "None" : "")));
    mainsColumn.setCellValueFactory(string -> new SimpleStringProperty(
        string.getValue().getMains().toString().replace("[", "").replace("]", "")
            + (string.getValue().getMains().isEmpty() ? "None" : "")));
    dessertsColumn.setCellValueFactory(string -> new SimpleStringProperty(
        string.getValue().getDesserts().toString().replace("[", "").replace("]", "")
            + (string.getValue().getDesserts().isEmpty() ? "None" : "")));
    drinksColumn.setCellValueFactory(string -> new SimpleStringProperty(
        string.getValue().getDrinks().toString().replace("[", "").replace("]", "")
            + (string.getValue().getDrinks().isEmpty() ? "None" : "")));
    costColumn.setCellValueFactory(string -> new SimpleStringProperty(
        "£" + Double.toString(string.getValue().getTotalCost()) + "0"));
    timeColumn.setCellValueFactory(string -> new SimpleStringProperty(string.getValue().getTime()));
    statusColumn.setCellValueFactory(
        string -> new SimpleStringProperty(string.getValue().getOrderStatus().toString()));
  }

  /**
   * Button to return to the home page.
   */

  @FXML
  private void btnHome() {
    database.removeKitchenStaff(cafeModel.getUser().getHostname());
    cafeModel.setUser(null);
    cafeModel.setViewController(null);
    NewView.load(homeButton, "/fxml/login.fxml");
  }
}
