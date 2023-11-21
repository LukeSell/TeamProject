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
 * Controller of the Edit Tables view.
 *
 */

public class EditTablesController {

  @FXML
  private Text tableSeatsText;

  @FXML
  private Button homeBut;

  @FXML
  private TextField tableSeats;

  @FXML
  private Button removeTableButton;

  @FXML
  private Button managerButton;

  @FXML
  private Line line;

  @FXML
  private Button addTableButton;

  @FXML
  private Text deleteTableText;

  @FXML
  private ChoiceBox<Table> deleteTableBox;

  @FXML
  private Line line2;

  @FXML
  private TextField tableNumber;

  @FXML
  private Text tableNumberText;

  private int obtainedTableNumber;
  private int obtainedTableSeats;

  /**
   * Initialises all elements of the view.
   */
  @FXML
  private void initialize() throws SQLException {
    deleteTableBox.setItems(DatabaseConnection.getDatabase().tableList());
  }

  /**
   * Button method which loads the login page.
   */
  @FXML
  private void goHome() {
    NewView.load(homeBut, "/fxml/login.fxml");
  }

  /**
   * Button method which loads manager page.
   */
  @FXML
  private void goManager() {
    NewView.load(managerButton, "/fxml/ManagerView.fxml");
  }

  /**
   * Adds table to the table list, obtaining all of the information from the text boxes.
   *
   * @param event the event that occurs to trigger the method.
   */
  @FXML
  void addTable(ActionEvent event) throws SQLException {
    obtainedTableNumber = Integer.parseInt(tableNumber.getText());
    obtainedTableSeats = Integer.parseInt(tableSeats.getText());

    DatabaseConnection.getDatabase().updateTable(obtainedTableNumber, obtainedTableSeats);

    tableNumber.setText("");
    tableSeats.setText("");

    initialize();
  }

  /**
   * Removes table from the table list.
   *
   * @param event the event that occurs to trigger the method.
   */
  @FXML
  void removeTable(ActionEvent event) throws SQLException {
    DatabaseConnection.getDatabase().deleteTable(deleteTableBox.getValue().getNumber());
    deleteTableBox.setValue(null);
    initialize();
  }
}
