package cafe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Platform;

/**
 * Creates a server that waits for another user to attempt to connect to this
 * user so that a notification can be received.
 *
 * @author Luke Sell
 * 
 * @see InternetConnection
 * @see CafeModel#updateNotifications(String, String)
 */

public class ServerRunner implements Runnable {

  private String titleMessage;
  private String infoMessage;

  /**
   * Receives a notification from another user and updates the application
   * accordingly.
   */

  private void notifyUser() {
    Platform
        .runLater(() -> CafeModel.getCafeModel().updateNotifications(titleMessage, infoMessage));
  }

  /**
   * Once a connection is established the notification is read and the user is
   * notified on the GUI.
   */

  public void run() {
    try {
      ServerSocket receive = new ServerSocket();
      receive.setReuseAddress(true);
      receive.bind(new InetSocketAddress(9300));
      do {
        Socket client = receive.accept();
        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        titleMessage = input.readLine();
        infoMessage = input.readLine();
        client.close();
        notifyUser();
      } while (!titleMessage.equals(""));
      receive.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
