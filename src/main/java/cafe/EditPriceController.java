package cafe;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * The interface for manager to change the price for each food.
 *
 * @author Bo Zhao.
 */


public class EditPriceController {

  @FXML
  private Button homeBut;

  @FXML
  private Button editMenuButton;

  @FXML
  private Line line;

  @FXML
  private Button changePrice;

  @FXML
  private Button checkButton;

  @FXML
  private Text orginalPrice;

  @FXML
  private TextField newPrice;

  @FXML
  private ChoiceBox<Food> meals;

  @FXML
  private Text currentMeal;

  private String curMeal = "";

  @FXML
  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

  @FXML
  private void initialize() throws SQLException {
    changePrice.setDisable(true);
    meals.setItems(DatabaseConnection.getDatabase().getMenu("entire"));
    curMeal = "";
  }

  /**
   * Button which loads back to the login home screen.
   */
  @FXML
  public void goHome() {
    NewView.load(homeBut, "/fxml/login.fxml");
  }

  /**
   * Button which loads back to the edit menu view.
   */
  @FXML
  void goEditMenu() {
    NewView.load(editMenuButton, "/fxml/editMenuView.fxml");
  }

  /**
   * check the current price of that meal, by calling the get meal price method
   * if the price is 0 the item is not found.
   */
  @FXML
  public void btnCheck() {
    if (meals.getValue() != null) {

      String meal = meals.getValue().getMeal();
      curMeal = meal;
      currentMeal.setText(meal);
      double price = DatabaseConnection.getDatabase().getMealPrice(meal);
      if (price == 0) {
        orginalPrice.setText("Not Found");
      } else {
        orginalPrice.setText("�" + price + "0");
        changePrice.setDisable(false);
      }
    }
  }

  /**
   * Confirm and submit the change of price to database. If an invalid input is entered a
   * notification will be shown to the user.
   * @throws SQLException deals with SQL exceptions.
   */
  @FXML
  public void btnChange() throws SQLException {
    if (!curMeal.equals("") && newPrice.getText() != "" && isNumeric(newPrice.getText())) {
      DatabaseConnection.getDatabase().updatePrice(curMeal, Float.parseFloat(newPrice.getText()));
    } else {
      alert.setTitle("Unvalid input");
      alert.setHeaderText("");
      alert.setContentText("Please input valid number.");
      alert.showAndWait();
    }
    initialize();
  }

  /**
   * Check if the price input is number.
   *
   * @param str is the price input as string.
   * @return true if input can be a number.
   */

  public boolean isNumeric(String str) {
    for (int i = str.length(); --i >= 0; ) {
      if (!Character.isDigit(str.charAt(i)) && str.charAt(i) != '.') {
        return false;
      }
      if (i == 0 && i == str.length() - 1) {
        if (str.charAt(i) != '.') {
          return false;
        }
      }
    }
    return true;
  }
}
