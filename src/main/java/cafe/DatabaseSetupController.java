package cafe;

import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller for the Database Setup view.
 * 
 * @author Bo
 */

public class DatabaseSetupController {

  @FXML
  private Button createTable;

  @FXML
  private TextField tabName;

  @FXML
  private ChoiceBox<String> tables0;

  @FXML
  private ChoiceBox<String> tables1;

  @FXML
  private Button btnHome;

  @FXML
  private TextArea attriName;

  @FXML
  private Button dropTable;

  @FXML
  private Button insert;

  @FXML
  private Text predicates;

  @FXML
  private TextArea inputAttributes;

  @FXML
  private Button managerButton;

  @FXML
  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

  @FXML
  private void initialize() throws SQLException {
    ObservableList<String> tables = DatabaseConnection.getDatabase().allTablesName();
    tables1.setItems(tables);
    tables0.setItems(tables);
    tabName.setText("");
    attriName.setText("");
    insert.setDisable(true);
  }

  /**
   * Button method which loads the login page.
   */
  @FXML
  private void goHome() {
    NewView.load(btnHome, "/fxml/login.fxml");
  }

  /**
   * create a new table in database with name and attributes.
   * 
   * @throws SQLException if there is an error communicating with database
   */

  @FXML
  private void btnCreate() throws SQLException {
    if (!tabName.getText().trim().equals("") & !attriName.getText().trim().equals("")) {
      DatabaseConnection.getDatabase()
          .createTable(tabName.getText() + "(" + attriName.getText() + ")");
      initialize();
    }
  }

  /**
   * Button method for returning back to manager view
   *
   */

  @FXML
  private void btnManagerR() {
    NewView.load(managerButton, "/fxml/ManagerView.fxml");
  }

  /**
   * drop a table in database based on value.
   * 
   * @throws SQLException if there is an error communicating with database
   */
  
  @FXML
  private void btnDrop() throws SQLException {
    if (!tables0.getValue().equals("")) {
      DatabaseConnection.getDatabase().dropTable(tables0.getValue());
      tables0.setValue(null);
      initialize();
    }
  }

  /**
   * insert a group of data into database.
   * 
   * @throws SQLException if there is an error communicating with database
   */
  
  @FXML
  private void btnInsert() throws SQLException {
    if (!inputAttributes.getText().trim().equals("")) {
      String[] attri = inputAttributes.getText().split(",");
      DatabaseConnection.getDatabase().insertIntoTable(tables1.getValue(), attri);
      inputAttributes.setText("");
    }
  }
}
