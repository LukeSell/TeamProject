package cafe;

import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * The view for customer to pay.
 *
 * @author Bo Zhao.
 */

public class PaymentViewController implements ViewController {

  @FXML
  private TextArea foodPur;
  
  @FXML
  private Button cardbut;
  
  @FXML
  private Button cashbut;
  
  @FXML
  private Button paypalbut;
  
  @FXML
  private TextField tipBox;

  @FXML
  private Button homebut;

  @FXML
  private Button resetbut;

  @FXML
  private Button tipConfirmButton;

  @FXML
  private ListView<TextNotification> notificationsView;

  @FXML
  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

  private CafeModel cafeModel = CafeModel.getCafeModel();
  private DatabaseConnection database = DatabaseConnection.getDatabase();
  private ObservableList<TextNotification> notificationsList = FXCollections
      .observableArrayList();
  private Order order;
  private double tipAmount = 0;
  private int tipPercentage = 0;

  /**
   * Initialises elements of the view.
   */
  
  @FXML
  private void initialize() {
    cafeModel.setViewController(this);
    order = database.readOrder(database.readCustomerOrderNumber(cafeModel.getUser().getHostname()));
    foodPur.setText(order.toString());
  }

  /**
   * Button to redirect to home page and remove the active user.
   */

  @FXML
  public void butHome() {
    cafeModel.setViewController(null);
    NewView.load(homebut, "/fxml/MainMenu.fxml");
  }

  public void refresh() {

  }

  /**
   * Updates the notifications.
   *
   * @param textNotification the text to update the notification with
   */

  public void updateNotifications(TextNotification textNotification) {
    notificationsList.add(0, textNotification);
    notificationsView.setItems(notificationsList);

  }

  /**
   * Remove a notification.
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
   * Button to confirm tip amount.
   */

  @FXML
  private void btnTipConfirm() {
    tipPercentage = Integer.parseInt(tipBox.getText());
    order.setTotalCost(order.getTotalCost() - tipAmount);
    double tipTemp = order.getTotalCost() * (tipPercentage / 100.0);
    order.setTotalCost(order.getTotalCost() + tipTemp);
    tipAmount = tipTemp;
    database.saveOrder(order);
    foodPur.setText(order.toString());

  }

  /**
   * reset the input and update the order information.
   */
  
  @FXML
  public void butReset() {
    initialize();
  }

  /**
   * paid by cash.
   */
  
  @FXML
  public void butCash() {
    alert.setTitle("Cash Payment");
    alert.setHeaderText("");
    alert.setContentText("Are you sure paying with cash?");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      cafeModel.updateOrder(order);
      String usernamex = database.readWaiterHostname(Integer.toString(order.getTableNumber()));
      AlertNotification.send(usernamex, "Customer Payed!",
          "Table " + Integer.toString(order.getTableNumber()) + " has Payed, the tip is "
              + tipPercentage + "%!");
    } else {
      initialize();
    }
  }

  /**
   * paid by card.
   */
  
  @FXML
  public void butCard() {
    alert.setTitle("Card Payment");
    alert.setHeaderText("");
    alert.setContentText("Are you sure paying with card?");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      cafeModel.updateOrder(order);
      String usernamex = database.readWaiterHostname(Integer.toString(order.getTableNumber()));
      AlertNotification.send(usernamex, "Customer Payed!",
          "Table " + Integer.toString(order.getTableNumber()) + " has Payed, the tip is "
              + tipPercentage + "%!");
    } else {
      initialize();
    }
  }

  /**
   * paid by pay pal(input username and password).
   */
  
  @FXML
  public void butPaypal() {
    Dialog<Pair<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Paypal");
    dialog.setHeaderText("Login you paypal account");
    dialog.setGraphic(new ImageView(this.getClass().getResource("/images/paypal.jpg").toString()));

    ButtonType loginButtonType = new ButtonType("Login", ButtonData.APPLY);
    dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField username = new TextField();
    username.setPromptText("Username");
    PasswordField password = new PasswordField();
    password.setPromptText("Password");

    grid.add(new Label("Username:"), 0, 0);
    grid.add(username, 1, 0);
    grid.add(new Label("Password:"), 0, 1);
    grid.add(password, 1, 1);

    Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
    loginButton.setDisable(true);
    username.textProperty().addListener((observable, oldValue, newValue) -> {
      loginButton.setDisable(newValue.trim().isEmpty());
    });
    
    dialog.getDialogPane().setContent(grid);
    Platform.runLater(() -> username.requestFocus());
    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == loginButtonType) {
        return new Pair<>(username.getText(), password.getText());
      }
      return null;
    });
    Optional<Pair<String, String>> result = dialog.showAndWait();
    if (result.get() != null) {
      cafeModel.updateOrder(order);
      String usernamex = database.readWaiterHostname(Integer.toString(order.getTableNumber()));
      AlertNotification.send(usernamex, "Customer Payed!",
          "Table " + Integer.toString(order.getTableNumber()) + " has Payed, the tip is "
              + tipPercentage + "%!");
    }
  }
}
