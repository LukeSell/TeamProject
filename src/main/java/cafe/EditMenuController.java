package cafe;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * Controller for the editMenu view.
 */

public class EditMenuController {

  @FXML
  private Text mealDescLabel;

  @FXML
  private Text mealTypeLabel;

  @FXML
  private Text mealText;

  @FXML
  private Line line;

  @FXML
  private TextField foodTime;

  @FXML
  private TextField foodType;

  @FXML
  private TextField foodTitle;

  @FXML
  private Text title;

  @FXML
  private Button addToMenuButton;

  @FXML
  private Text mealCostLabel;

  @FXML
  private Button homeBut;

  @FXML
  private TextField foodPrice;

  @FXML
  private TextField foodAll;

  @FXML
  private Text mealAllLabel;

  @FXML
  private TextField foodDesc;

  @FXML
  private Text mealNutLabel;

  @FXML
  private TextField foodNut;

  @FXML
  private Line line2;

  @FXML
  private Text DeleteMealTitle;

  @FXML
  private ChoiceBox<Food> DeleteFoodNameText;

  @FXML
  private Text mealPrepText;

  @FXML
  private Button RemoveMenuButton;

  @FXML
  private Button editPriceButton;

  @FXML
  private Text mealImageURL;

  @FXML
  private TextField foodURL;

  @FXML
  private Button managerButton;


  /**
   * Initial method which sets the items to the dropdowns (choice boxes) from the Food table.
   */
  
  @FXML
  private void initialize() throws SQLException {
    DeleteFoodNameText.setItems(DatabaseConnection.getDatabase().getMenu("entire"));
  }

  private String obtainedFoodName = null;
  private String obtainedFoodDesc = null;
  private String obtainedFoodType = null;
  private String obtainedFoodNutrition = null;
  private String obtainedFoodAllergies = null;
  private String obtainedFoodCost = null;
  private String obtainedFoodTime = null;
  private String obtainedURL = null;

  /**
   * Adds food to the menu, obtaining all of the information from the text boxes, and then
   * initialises again.
   *
   * @param event the event that occurs to trigger the method.
   */

  @FXML
  void addFood(ActionEvent event) throws SQLException {
    obtainedFoodName = foodTitle.getText();
    obtainedFoodDesc = foodDesc.getText();
    obtainedFoodType = foodType.getText();
    obtainedFoodNutrition = foodNut.getText();
    obtainedFoodAllergies = foodAll.getText();
    obtainedFoodCost = foodPrice.getText();
    obtainedFoodTime = foodTime.getText();
    obtainedURL = foodURL.getText();

    DatabaseConnection.getDatabase().updateMenu("Food", obtainedFoodName, obtainedFoodDesc,
        obtainedFoodType, obtainedFoodNutrition, obtainedFoodAllergies, obtainedFoodCost,
        obtainedFoodTime, obtainedURL);

    foodTitle.setText("");
    foodDesc.setText("");
    foodType.setText("");
    foodNut.setText("");
    foodAll.setText("");
    foodPrice.setText("");
    foodTime.setText("");

    initialize();
  }

  /**
   * Removes item from menu, once selected from the choice box.
   *
   * @param event the event that occurs to trigger the method.
   */
  
  @FXML
  void removeFoodItem(ActionEvent event) throws SQLException {
    DatabaseConnection.getDatabase().deleteMenu("Food", DeleteFoodNameText.getValue().getMeal());
    DeleteFoodNameText.setValue(null);
    initialize();
  }

  /**
   * Button which takes you back to the login screen.
   */
  
  @FXML
  private void goHome() {
    NewView.load(homeBut, "/fxml/login.fxml");
  }

  /**
   * Button method to load manager view.
   */
  
  @FXML
  void goManager() {
    NewView.load(managerButton, "/fxml/ManagerView.fxml");
  }

  /**
   * Button method which loads edit price view.
   */
  
  @FXML
  private void editPrice() {
    NewView.load(editPriceButton, "/fxml/EditPriceView.fxml");
  }
}
