package cafe;

/**
 * Updates the view controller with a notification when the client requests help.
 * 
 * @author luke sell
 */

public interface ViewController {
  
  /**
   * Refreshes the view.
   */
  public void refresh();
  
  /**
   * Updates the notification.
   * @param textNotification  the text to update the notification with
   */
  
  public void updateNotifications(TextNotification textNotification);

}
