package cafe;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the table class.
 *
 */

public class TestTable {

  private Table table;

  /**
   * Setting up a new table object.
   */
  @Before
  public void setup() {
    table = new Table(1, 6);
  }

  /**
   * Testing the has seats command returns the expected outcome.
   */

  @Test
  public void testHasSeatsTrue() {
    assertEquals("", table.hasSeats(3), true);
  }

  /**
   * Testing has seats returns the appropriate outcome.
   */

  @Test
  public void testHasSeatsFalse() {
    assertEquals("", table.hasSeats(77), false);
  }

}
