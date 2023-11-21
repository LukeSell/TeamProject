package cafe;

/**
 * Interface that sets the standards of the different order status.
 */

public interface OrderHandler {

  /**
   * Method outlined to act as behaviour.
   */

  public void handle();

  /**
   * Method to return next order status in the chain.
   *
   * @return next OrderStatus
   */

  public OrderStatus next();

}
