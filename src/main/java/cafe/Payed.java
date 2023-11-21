package cafe;

/**
 * Class to represent the order status payed.
 *
 * @author luke sell
 */

public class Payed implements OrderHandler {
  
  private static final Payed payed = new Payed();
  
  private Payed() {
    
  }
  
  public static Payed getHandler() {
    return payed;
  }
  
  public void handle() {
    
  }
  
  public OrderStatus next() {
    return OrderStatus.INVALID;
  }

}
