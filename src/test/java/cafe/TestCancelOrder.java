package cafe;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the ability to cancel an order.
 * 
 * @author Liam Jones
 */

public class TestCancelOrder {

  /**
   * Tests the removeOrder method in the DatabaseConnection file.
   */

  @Test
  public void testCancelOrder() {
    int tableNumber = 1;
    DatabaseConnection connection = DatabaseConnection.getDatabase();
    assertEquals("Successfully removed order from table 1: ", true,
        connection.removeOrder(tableNumber));
  }

}
