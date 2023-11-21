package cafe;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;

/**
 * Controller for the register view.
 *
 * @author thanushen
 */

public class RegisterController {

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private Button registerBtn;

  @FXML
  private PasswordField regPassword;

  @FXML
  private PasswordField regUsername;

  @FXML
  private PasswordField regPassword1;

  @FXML
  private Button homeButton;

  TextInputDialog dialog = new TextInputDialog("");

  /**
   * Button that loads home page.
   */

  @FXML
  void goHome() {
    NewView.load(homeButton, "/fxml/login.fxml");
  }

  /**
   * Button that registers account with inputted account details.
   */

  @FXML
  void btnRegister(ActionEvent event) {
    LoginBridge.registerAccountRedirect(regUsername.getText(), regPassword.getText(), registerBtn);
  }
}
