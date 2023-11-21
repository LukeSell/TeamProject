package cafe;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Initial Tests for database connection. These tests initially were developed
 * to test the initial implementations of methods, however after further
 * development these tests became redundant, hence why are ignored.
 * 
 * @author Author: Michael Moorman
 */

public class DatabaseConnectionTest {

  /**
   * Setting up connection to local host.
   */

  @Before
  public void connect() {

  }

  private DatabaseConnection database = DatabaseConnection.getDatabase();
  String table = "test (Meal varchar(50) NOT NULL, " + "Meal_description varchar(200) NOT NULL, "
      + "Meal_Type varchar(10) NOT NULL, " + "Nutritional_info varchar(100) NOT NULL, "
      + "Allergy_info varchar(100), " + "Cost varchar(5) NOT NULL)";

  /**
   * Setting up connection to local host
   */

  /**
   * Test which queries the table for meals as mains, and checks against one of
   * the expected outcomes.
   * 
   * @throws SQLException deals with SQL exceptions.
   */
  @Test
  @Ignore
  public void testGetMainMenu() throws SQLException {
    database.dropTable("test");

    database.createTable(table);
    // database.insertIntoTable("test", "/text/TestFile.txt");

    /*
     * String s = ConnectAndRead.mainMenu(connection, "test"); assertEquals(s,
     * "Chicken Burger, Chicken with lettuce and tomato in bun, MAIN, 295 kcal, CONTAINS : Eggs and Gluten and Wheat and Soy, £7.50"
     * );
     */
  }

  /**
   * Test which queries the table for meals as starters, and checks against the
   * one expected outcome.
   * 
   * @throws SQLException deals with SQL exceptions.
   */
  @Test
  @Ignore
  public void testGetStarterMenu() throws SQLException {
    database.dropTable("test");

    database.createTable(table);
    // database.insertIntoTable("test", "/text/TestFile.txt");

    /*
     * String s = ConnectAndRead.starterMenu(connection, "test"); assertEquals(s,
     * "Calamari, Deepfried squid with lemon, STARTER, 175 kcal, No allergy Info, £4.50"
     * );
     */
  }

  /**
   * Test which queries the table for meals as desserts, and checks against the
   * one expected outcome.
   * 
   * @throws SQLException deals with SQL exceptions.
   */
  @Test
  @Ignore
  public void testGetDessertMenu() throws SQLException {
    database.dropTable("test");

    database.createTable(table);
    // database.insertIntoTable("test", "/text/TestFile.txt");

    /*
     * String s = ConnectAndRead.dessertMenu(connection, "test"); assertEquals(s,
     * "Cheese Cake,  Cheese cake with ice cream, DESSERT, 150 kcal,  CONTAINS : CHEESE, £4.00"
     * );
     */
  }

  /**
   * Test which queries the table for meals as drinks, and checks against the one
   * expected outcome.
   * 
   * @throws SQLException deals with SQL exceptions.
   */
  @Test
  @Ignore
  public void testGetDrinksMenu() throws SQLException {
    database.dropTable("test");

    database.createTable(table);
    // database.insertIntoTable("test", "/text/TestFile.txt");

    /*
     * String s = ConnectAndRead.drinksMenu(connection, "test"); assertEquals(s,
     * "Red Wine,  Merlot red wine, DRINK,  50 kcal,  CONTAINS : Alcohol, £3.00");
     */
  }

  /**
   * Test comparing the results before and after new entry was added to the table
   * (author Michael Moorman, Liam Jones).
   */
  @Test
  public void addToMenu() {
    database.dropTable("test");
    database.createTable(table);/*
                                 * try { Statement s = connection.createStatement(); ResultSet
                                 * result1 = s.executeQuery("SELECT COUNT(*) FROM test");
                                 * database.updateMenu("test", "Milk", "Glass of Milk", "DRINK",
                                 * "50 kcal", "CONTAINS : Dairy", "2.00"); ResultSet result2 =
                                 * s.executeQuery("SELECT COUNT(*) FROM test"); assertFalse(result1
                                 * == result2); } catch (SQLException e) { e.printStackTrace(); }
                                 */
    database.dropTable("test");
  }

  /**
   * Test comparing the results before and after an entry has been deleted from
   * the table (author Michael Moorman, Liam Jones).
   */

  @Test
  public void deleteFromMenu() {/*
                                 * try { Statement s = connection.createStatement(); ResultSet
                                 * result1 = s.executeQuery("SELECT COUNT(*) FROM test");
                                 * ConnectAndRead.deleteMenu("test", "Milk"); ResultSet result2 =
                                 * s.executeQuery("SELECT COUNT(*) FROM test"); assertFalse(result1
                                 * == result2); } catch (SQLException e) { e.printStackTrace(); }
                                 */
  }

  /**
   * Test for adding to the menu, creates a table and inserts current rows into it
   * from text file, then adds to the menu, then adds a new row to the text file
   * and shows in terminal (and through assert equals) that one more row has been
   * added to the file.
   * 
   * @throws IOException handles IOexceptions.
   */
  @Test
  @Ignore
  public void testAddMenu() throws IOException {
    database.dropTable("test");

    FileReader fr = new FileReader("/test/TestFile.txt");
    Scanner s = new Scanner(fr);

    database.createTable(table);

    int rows = 0;
    while (s.hasNextLine()) {
      rows = rows + 1;
      s.nextLine();
    }
    s.close();
    System.out.println(rows);

    // assertEquals(ConnectAndRead.insertIntoTable(connection, "test",
    // "/text/TestFile.txt"), (rows));

    database.updateMenu("/text/TestFile.txt", "Scrambled Eggs",
        "Bread with scrambled eggs with salt and pepper", "Main", "100 kcal", "CONTAINS : Eggs",
        "4.50", "time : 5 mins", "www.scrambledEggs.com");

    database.dropTable("test");
    database.createTable(table);

    FileReader file = new FileReader("/text/TestFile.txt");
    Scanner st = new Scanner(file);

    int rows2 = 0;
    while (st.hasNextLine()) {
      rows2 = rows2 + 1;
      st.nextLine();
    }
    st.close();
    System.out.println(rows2);
    // assertEquals(ConnectAndRead.insertIntoTable(connection, "test",
    // "/text/TestFile.txt"), (rows2));
  }

  @After
  public void close() {
    database.dropTable("test");
  }
}