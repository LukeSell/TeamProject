package cafe;

import javafx.scene.control.Button;

/**
 * This method acts as the bridge from the login/register page to other pages.
 * This ensures that login or register is made to the correct user role.
 *
 * @author thanushen
 */

public class LoginBridge {

  private static final CafeModel cafeModel = CafeModel.getCafeModel();
  private static final DatabaseConnection database = DatabaseConnection.getDatabase();

  /**
   * Method to check login with correct database and redirect to the correct page
   * by checking the maven profile used.
   *
   * @param username Inputted username.
   * @param password Inputted password.
   * @param stageRef Button of current stage to use as a reference to current
   *                 stage.
   */
  public static void loginRedirect(String username, String password, Button stageRef) {
    String profile = Config.getUser();
    if (profile.equals("Customer")) {
      Customer customer = UserFactory.getCustomerLogin(username, password);
      if (!customer.equals(null)) {
        database.saveCustomer(customer);
        cafeModel.setUser(customer);
        NewView.load(stageRef, "/fxml/MainMenu.fxml");
      }
    } else if (profile.equals("Waiter")) {
      Waiter waiter = UserFactory.getWaiterLogin(username, password);
      if (!waiter.equals(null)) {
        database.saveWaiter(waiter);
        cafeModel.setUser(waiter);
        NewView.load(stageRef, "/fxml/waiterview.fxml");
      }
    } else if (profile.equals("Kitchen")) {
      KitchenStaff kitchenStaff = UserFactory.getKitchenStaffLogin(username, password);
      if (!kitchenStaff.equals(null)) {
        database.saveKitchenStaff(kitchenStaff);
        cafeModel.setUser(kitchenStaff);
        NewView.load(stageRef, "/fxml/KitchenView.fxml");
      }
    } else if (profile.equals("Manager")) {
      Manager manager = UserFactory.getManagerLogin(username, password);
      if (!manager.equals(null)) {
        database.saveManager(manager);
        cafeModel.setUser(manager);
        NewView.load(stageRef, "/fxml/ManagerView.fxml");
      }
    }
  }

  /**
   * Method redirect to the correct page by checking the maven profile used with a
   * guest user object.
   *
   * @param username Inputted username.
   * @param hostname hostname of user.
   * @param stageRef Button of current stage to use as a reference to current
   *                 stage.
   */

  public static void guestRedirect(String username, String hostname, Button stageRef) {
    String profile = Config.getUser();
    if (profile.equals("Customer")) {
      Customer customer = new Customer((username.equals("") ? hostname : username), hostname);
      database.saveCustomer(customer);
      cafeModel.setUser(customer);
      NewView.load(stageRef, "/fxml/MainMenu.fxml");
    } else if (profile.equals("Waiter")) {
      Waiter waiter = new Waiter((username.equals("") ? hostname : username), hostname);
      database.saveWaiter(waiter);
      cafeModel.setUser(waiter);
      NewView.load(stageRef, "/fxml/waiterview.fxml");
    } else if (profile.equals("Kitchen")) {
      KitchenStaff kitchenStaff = new KitchenStaff((username.equals("") ? hostname : username),
          hostname);
      database.saveKitchenStaff(kitchenStaff);
      cafeModel.setUser(kitchenStaff);
      NewView.load(stageRef, "/fxml/KitchenView.fxml");
    } else if (profile.equals("Manager")) {
      Manager manager = new Manager((username.equals("") ? hostname : username), hostname);
      database.saveManager(manager);
      cafeModel.setUser(manager);
      NewView.load(stageRef, "/fxml/ManagerView.fxml");
    }
  }

  /**
   * Method to register new user with correct database and redirect to the correct
   * page by checking the maven profile used.
   *
   * @param username Inputted username.
   * @param password Inputted password.
   * @param stageRef Button of current stage to use as a reference to current
   *                 stage.
   */

  public static void registerAccountRedirect(String username, String password, Button stageRef) {
    String profile = Config.getUser();
    if (profile.equals("Waiter")) {
      database.saveWaiterLogin(new Waiter(username, Integer.toString(password.hashCode()),
          InternetConnection.getConnection().getHost()));
    } else if (profile.equals("Customer")) {
      database.saveCustomerLogin(new Customer(username,
          Integer.toString(password.hashCode()), InternetConnection.getConnection().getHost()));

    } else if (profile.equals("Kitchen")) {
      database.saveKitchenStaffLogin(new KitchenStaff(username,
          Integer.toString(password.hashCode()), InternetConnection.getConnection().getHost()));

    } else if (profile.equals("Manager")) {

      database.saveManagerLogin(new Manager(username,
          Integer.toString(password.hashCode()), InternetConnection.getConnection().getHost()));
    }
    NewView.load(stageRef, "/fxml/login.fxml");
  }
}
