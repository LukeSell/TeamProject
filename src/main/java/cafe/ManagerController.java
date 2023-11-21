package cafe;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller for the manager view.
 */

public class ManagerController {

  private DatabaseConnection database = DatabaseConnection.getDatabase();
  private CafeModel cafeModel = CafeModel.getCafeModel();

  @FXML
  private Button homeButton;

  @FXML
  private Button refreshButton;

  @FXML
  private Button exitButton;

  @FXML
  private ChoiceBox<Customer> customerButton;

  @FXML
  private ChoiceBox<Waiter> waiterButton;

  @FXML
  private ChoiceBox<Table> tableButton;

  @FXML
  private Button assignButton;

  @FXML
  private Button editMenuButton;

  @FXML
  private TableView<Customer> customerTable;

  @FXML
  private TableView<Waiter> waiterTable;

  @FXML
  private TableView<Table> tableTable;

  @FXML
  private Button editTablesButton;

  @FXML
  private Button manageDatabaseButton;

  @FXML
  private TableColumn<Table, Integer> tableNumberColumn;

  @FXML
  private TableColumn<Table, Integer> seatsColumn;

  @FXML
  private TableColumn<Table, String> waiterColumn;

  @FXML
  private TableColumn<Table, String> statusColumn;

  @FXML
  private TableColumn<Customer, String> usernameCustomerColumn;

  @FXML
  private TableColumn<Customer, Integer> tableCustomerAssignedColumn;

  @FXML
  private TableColumn<Customer, Integer> orderNumberColumn;

  @FXML
  private TableColumn<Waiter, String> usernameWaiterColumn;

  @FXML
  private TableColumn<Waiter, Integer> tableWaiterAssignedColumn;

  /**
   * Initialise all elements of the view.
   */

  @FXML
  private void initialize() {
    customerButton.setItems(database.customerList());
    waiterButton.setItems(database.waiterList());
    tableButton.setItems(database.tableList());
    tableTable.setItems(database.tableList());
    customerTable.setItems(database.customerList());
    waiterTable.setItems(database.waiterList());
    tableNumberColumn.setCellValueFactory(
        string -> new SimpleIntegerProperty(string.getValue().getNumber()).asObject());
    seatsColumn.setCellValueFactory(
        string -> new SimpleIntegerProperty(string.getValue().getSeats()).asObject());
    waiterColumn.setCellValueFactory(
        string -> new SimpleStringProperty(string.getValue().getWaiter().toString()));
    statusColumn.setCellValueFactory(
        string -> new SimpleStringProperty(string.getValue().getStatus().toString()));
    usernameCustomerColumn
        .setCellValueFactory(string -> new SimpleStringProperty(string.getValue().getUsername()));
    tableCustomerAssignedColumn.setCellValueFactory(
        string -> new SimpleIntegerProperty(string.getValue().getTableNumber()).asObject());
    orderNumberColumn.setCellValueFactory(
        string -> new SimpleIntegerProperty(string.getValue().getOrderNumber()).asObject());
    usernameWaiterColumn
        .setCellValueFactory(string -> new SimpleStringProperty(string.getValue().getUsername()));
    tableWaiterAssignedColumn.setCellValueFactory(
        string -> new SimpleIntegerProperty(string.getValue().getTableNumber()).asObject());
  }

  /**
   * Assigns customer to waiter and table.
   */

  @FXML
  void btnAssign() {
    if ((customerButton.getValue() != null) && (waiterButton.getValue() != null) && (
        tableButton.getValue() != null)) {
      database.setCustomerTableNumber(customerButton.getValue().getHostname(),
          tableButton.getValue().getNumber());
      database.setWaiterTableNumber(waiterButton.getValue().getHostname(),
          tableButton.getValue().getNumber());
      database.setTableCustomerWaiter(customerButton.getValue().getHostname(),
          waiterButton.getValue().getHostname(),
          Integer.toString(tableButton.getValue().getNumber()));
    }
  }

  /**
   * Button to redirect to the edit menu page.
   */

  @FXML
  void btnEditMenu() {
    NewView.load(editMenuButton, "/fxml/editMenuView.fxml");
  }

  /**
   * Button to redirect to the edit tables page.
   */

  @FXML
  void btnEditTables() {
    NewView.load(editTablesButton, "/fxml/editTablesView.fxml");
  }

  /**
   * Button to exit program.
   */

  @FXML
  void btnExit() {
    exitButton.getScene().getWindow().hide();
  }

  /**
   * Button to refresh the elements of the view.
   */

  @FXML
  private void btnRefresh() {
    initialize();
  }

  /**
   * Button to return to the home page.
   */

  @FXML
  void btnHome() {
    database.removeManager(cafeModel.getUser().getHostname());
    cafeModel.setUser(null);
    NewView.load(homeButton, "/fxml/login.fxml");
  }

  /**
   * Button to redirect to the manage database page.
   */

  @FXML
  void btnManageDatabase() {
    NewView.load(manageDatabaseButton, "/fxml/DatabaseSetup.fxml");
  }
}
