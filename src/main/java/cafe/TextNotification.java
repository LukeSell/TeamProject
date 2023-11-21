package cafe;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class that represents a text notification that will be displayed to the user.
 *
 * @author Luke Sell
 * 
 * @see InternetConnection
 * @see CafeModel#updateNotifications(String, String)
 */

public class TextNotification {

  private String titleMessage;
  private String infoMessage;
  private String timeStamp = new SimpleDateFormat("HH:mm:ss")
      .format(Calendar.getInstance().getTime());

  public TextNotification(String titleMessage, String infoMessage) {
    this.titleMessage = titleMessage;
    this.infoMessage = infoMessage;
  }

  @Override
  public String toString() {
    return titleMessage + "\n" + infoMessage + "\n" + timeStamp;
  }
}
