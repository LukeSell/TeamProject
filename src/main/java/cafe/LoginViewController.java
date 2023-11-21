package cafe;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller for the login view.
 *
 * @author Luke Sell
 */

public class LoginViewController {

  @FXML
  private Button loginButton;

  @FXML
  private Button guestButton;

  @FXML
  private TextField userUsername;

  @FXML
  private PasswordField userPassword;

  @FXML
  private Button register;

  @FXML
  private Label buildLabel;

  @FXML
  private ChoiceBox<String> roleSelect;

  private Alert incorrectLogin = new Alert(AlertType.INFORMATION);

  /**
   * Initialises all elements of the view.
   */

  @FXML
  private void initialize() {
    if (!Config.getUser().equals("Customer")) {
      guestButton.setVisible(false);
    }
    buildLabel.setText(Config.getUser() + " v" + Config.getVersion());
    incorrectLogin.setHeaderText("Login");
    incorrectLogin.setContentText("Incorrect login details.");
  }


  /**
   * Button to redirect to the login page.
   */

  @FXML
  private void btnRegister() {
    NewView.load(register, "/fxml/register.fxml");
  }

  /**
   * Button to login with inputted details.
   */

  @FXML
  private void btnLogin() {
    LoginBridge.loginRedirect(userUsername.getText(), userPassword.getText(), loginButton);
  }

  /**
   * Button to login as guest.
   */

  @FXML
  private void btnGuest() {
    LoginBridge.guestRedirect(userUsername.getText(), InternetConnection.getConnection().getHost(),
        guestButton);
  }
}
