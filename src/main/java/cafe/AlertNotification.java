package cafe;

/**
 * Adapter/facade for the Internet connection object, to make the sending of alert notifications
 * simpler.
 *
 * @author Luke Sell
 * 
 * @see InternetConnection
 */

public class AlertNotification {

  private static DatabaseConnection database = DatabaseConnection.getDatabase();
  private static InternetConnection connection = InternetConnection.getConnection();

  /**
   * Sends an alert notification to the specified user using the Internet connection object.
   *
   * @param username is the users identification on the database
   * @param titleMessage is the header text for the alert notification
   * @param infoMessage is the body text for the alert notification
   */

  public static void send(String username, String titleMessage, String infoMessage) {
    String ip = database.readIp(username);
    connection.open(ip, titleMessage, infoMessage);
  }

}
