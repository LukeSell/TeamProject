package cafe;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class which starts the javafx application and shows the GUI.
 * 
 * @author Luke Sell
 * 
 * @see CafeModel#exit()
 * @see InternetConnection#exit()
 * @see DatabaseConnection#exit()
 *
 */

public class Main extends Application {

  private static final int MAX_WIDTH = 1348;
  private static final int MAX_HEIGHT = 1012;

  
  /**
   * Initialises the GUI and sets exit methods to be called on exit.
   */
  
  @Override
  public void start(Stage stage) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
      Scene scene = new Scene(root, MAX_WIDTH, MAX_HEIGHT);
      stage.setScene(scene);
      stage.setOnHidden(e -> {
        CafeModel.getCafeModel().exit();
        InternetConnection.getConnection().exit();
        DatabaseConnection.getDatabase().exit();
        Platform.exit();
      });
      stage.setTitle("OAXACA Restaurant App");
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      throw new Error();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
