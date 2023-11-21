package cafe;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Class used to switch between different views.
 *
 * @author Luke Sell
 */

public class NewView {

  /**
   * Loads an FXML and draws it onto the stage of the button passed in.
   *
   * @param stageRef Button to reference stage
   * @param fxmlname File path of FXML
   */

  static void load(Button stageRef, String fxmlname) {
    try {
      Parent root = FXMLLoader.load(Main.class.getResource(fxmlname));
      Stage stage = (Stage) stageRef.getScene().getWindow();
      stage.getScene().setRoot(root);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      throw new Error();
    }
  }
}
