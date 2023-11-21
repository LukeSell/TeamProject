package cafe;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Allows communication, on a local network, between users of the application.
 *
 * @author Luke Sell
 */

public class InternetConnection {

  private static final InternetConnection internetConnection = new InternetConnection();
  private final DatabaseConnection database = DatabaseConnection.getDatabase();
  private final Thread serverThread;
  private final String ip;
  private final String deviceName;

  /**
   * Sets up the server and writes ip to database.
   */

  private InternetConnection() {
    try {
      deviceName = Inet4Address.getLocalHost().getHostName().replaceAll("-", "");
      ip = Inet4Address.getLocalHost().getHostAddress();
      database.writeIp(deviceName, ip);
      serverThread = new Thread(new ServerRunner());
      serverThread.start();
    } catch (UnknownHostException e) {
      e.printStackTrace();
      throw new InternetConnectionError();
    }
  }

  public static InternetConnection getConnection() {
    return internetConnection;
  }

  /**
   * Opens a connection to another user and sends them a message to be used in an
   * alert notification.
   *
   * @param ip           is the users address on the local network
   * @param titleMessage is the header text for the alert notification
   * @param infoMessage  is the body text for the alert notification
   */

  public void open(String ip, String titleMessage, String infoMessage) {
    try {
      Socket send = new Socket();
      send.connect(new InetSocketAddress(ip, 9300), 5000);
      PrintWriter output = new PrintWriter(send.getOutputStream(), true);
      output.println(titleMessage);
      output.println(infoMessage);
      send.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
      Alert alert = new Alert(AlertType.INFORMATION,
          "Connection Timeout, there was a problem sending the notifications!");
      alert.setHeaderText("Connection Timeout!");
      alert.showAndWait();
    }
  }

  /**
   * Closes the server and removes the ip from the database.
   */

  public void exit() {
    open(ip, "", "");
    try {
      serverThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    database.removeIp(deviceName);
  }

  public String getHost() {
    return deviceName;
  }
}
