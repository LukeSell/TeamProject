package cafe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class containing methods which communicate with the database.
 * 
 * @author Michael Moorman
 * @author Luke Sell
 */

public class DatabaseConnection {

  private static DatabaseConnection database = new DatabaseConnection();
  private Connection connection = null;

  public static DatabaseConnection getDatabase() {
    return database;
  }

  /**
   * Main method to run other methods, and initialise the connection to the
   * database.
   */

  private DatabaseConnection() {
    try {
      String username = "nlhszqbffujmgr";
      String password = "2c3fe16e65f4d9e2d51912562dd67a5f338e9e2710d11f33283b2a16fc17f3da";
      String database = "jdbc:postgresql://ec2-54-228-212-134.eu-west-1.compute.amazonaws."
          + "com:5432/ddkl8e85u0b4t5?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

      connectToDatabase(username, password, database);

      createTable("Food(Meal varchar(50) primary key NOT NULL, "
          + "Meal_description varchar(200) NOT NULL, " + "Meal_Type varchar(10) NOT NULL, "
          + "Nutritional_info varchar(100) NOT NULL, " + "Allergy_info varchar(100), "
          + "Cost varchar(5) NOT NULL,"
          + "CHECK(Meal_Type IN ('Starter', 'Main', 'Dessert', 'Drink')), "
          + "Cooking_Time varchar(10) NOT NULL," + "Food_image varchar(3072) NOT NULL)");

      /*
       * insertIntoTable("Food",
       * "Meal, Meal_description, Meal_Type, Nutritional_info, Allergy_info, Cost, Cooking_Time, food_image"
       * , "/text/FoodsAvailable.txt");
       */

      createTable(
          "Waiter(Username varchar(50) NOT NULL, Password varchar(50) NOT NULL, hostname varchar(50) primary key, tableNumber int)");
      createTable(
          "WaiterLogin(Username varchar(50) primary key NOT NULL, Password varchar(250) NOT NULL, hostname varchar(50), tableNumber varchar(10))");
      createTable(
          "Customer(username varchar(50), password varchar(50), hostname varchar(200) primary key NOT NULL, tableNumber int, orderNumber int)");
      createTable(
          "CustomerLogin(username varchar(50) primary key, password varchar(250), hostname varchar(200) NOT NULL, tableNumber varchar(10))");
      createTable(
          "Manager(username varchar(50) NOT NULL, password varchar(50) NOT NULL, hostname varchar(200) primary key NOT NULL)");
      createTable(
          "ManagerLogin(username varchar(50) primary key NOT NULL, password varchar(250) NOT NULL, hostname varchar(200) NOT NULL)");
      createTable(
          "KitchenStaff(username varchar(50) NOT NULL, password varchar(50) NOT NULL, hostname varchar(200) primary key NOT NULL)");
      createTable(
          "KitchenStaffLogin(username varchar(50) primary key NOT NULL, password varchar(250) NOT NULL, hostname varchar(200) NOT NULL)");

      createTable(
          "tables(Number int primary key, seats int, waiter varchar(50), status varchar(50))");

      createTable(
          "Orders(OrderNumber serial primary key, TableNumber int NOT NULL, Starters varchar(2000) NOT NULL, Mains varchar(2000), Desserts varchar(2000), Drinks varchar(2000), totalCost varchar(10), OrderPlaced varchar (50) NOT NULL, orderStatus varchar(50))");

      createTable("Ip(username varchar(50) primary key, ip varchar(50))");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates the connection to the database, returns the connection. Code taken
   * from lab 7 Dr. Iddo Tzameret's, so has no testing.
   * 
   * @param username users username.
   * @param password users password.
   * @param database databse we want to communicate with.
   */

  public void connectToDatabase(String username, String password, String database) {
    try {
      connection = DriverManager.getConnection(database, username, password);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Closes the current connection.
   */
  
  public void exit() {
    try {
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Drops Table from database if a table with the same name exists. Code taken
   * from lab 7 Dr. Iddo Tzameret's, so has no testing.
   * 
   * @param table table on the database
   */

  public void dropTable(String table) {
    try {
      Statement statement = connection.createStatement();
      statement.execute("DROP TABLE IF EXISTS " + table);
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates a table with the desired name and adds to the database. Code taken
   * from lab 7 Dr. Iddo Tzameret's, so has no testing.
   * 
   * @param table table to create
   */

  public void createTable(String table) {
    try {
      Statement statement = connection.createStatement();
      statement.execute("CREATE TABLE IF NOT EXISTS " + table);
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads code from desired file line by line and inserts it in to the names
   * table. Code taken from lab 7 Dr. Iddo Tzameret's, so has no testing.
   * 
   * @param table  table to insert into.
   * @param values table paramaters.
   * @param file   file inserting from.
   */

  public void insertIntoTable(String table, String values, String file) {
    String line = null;
    String[] splitLine = null;
    String createdLine = null;
    int count = 0;

    try {
      Statement s = connection.createStatement();
      BufferedReader br = new BufferedReader(
          new InputStreamReader(Main.class.getResourceAsStream(file)));
      while ((line = br.readLine()) != null) {
        splitLine = line.split(",");
        createdLine = "INSERT INTO " + table + "(" + values + ") VALUES (";
        for (int i = 0; i <= splitLine.length - 2; i++) {
          createdLine = createdLine + "'" + splitLine[i] + "',";
        }
        createdLine = createdLine + "'" + splitLine[splitLine.length - 1] + "')";
        s.addBatch(createdLine);
        if ((count % 100) == 0) {
          s.executeBatch();
        }
        count++;
      }
      s.executeBatch();
      br.close();
      s.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method creates a statement from a connection to communicate with the
   * database. It allows us to query the database.
   *
   * @param sql query or request string to the send to the database.
   */
  
  private void sqlExecuteUpdate(String sql) {
    try {
      Statement statement = connection.createStatement();
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
      Alert alert = new Alert(AlertType.INFORMATION,
          "Database Error, there is a problem with the database!");
      alert.setHeaderText("Database Error!");
      alert.showAndWait();
    }
  }

  /**
   * Saves waiter information to the Waiter table in the database.
   *
   * @param waiter takes the argument of the current user
   */
  
  public void saveWaiter(Waiter waiter) {
    sqlExecuteUpdate("INSERT INTO Waiter VALUES('" + waiter.getUsername() + "', '"
        + waiter.getPassword() + "', '" + waiter.getHostname() + "')");
  }

  /**
   * Saves waiter information to the Waiter table in the database.
   *
   * @param waiter takes the argument of the current user
   */
  
  public void saveWaiterLogin(Waiter waiter) {
    sqlExecuteUpdate("INSERT INTO WaiterLogin VALUES('" + waiter.getUsername() + "', '"
        + waiter.getPassword() + "', '" + waiter.getHostname() + "')");
  }

  /**
   * Saves customer information to the customer tables based on user.
   *
   * @param customer takes argument of the user (customer)
   */
  
  public void saveCustomer(Customer customer) {
    sqlExecuteUpdate("INSERT INTO Customer VALUES('" + customer.getUsername() + "', '"
        + customer.getPassword() + "', '" + customer.getHostname() + "')");
  }

  /**
   * Saves customer information to the customer login tables based on user.
   *
   * @param customer takes argument of the user (customer)
   */
  
  public void saveCustomerLogin(Customer customer) {
    sqlExecuteUpdate("INSERT INTO CustomerLogin VALUES('" + customer.getUsername() + "', '"
        + customer.getPassword() + "', '" + customer.getHostname() + "')");
  }

  /**
   * Saves kitchen staff information to the kithenStaff tables based on user.
   *
   * @param kitchenStaff takes argument of the user (kitchenStaff)
   */

  public void saveKitchenStaff(KitchenStaff kitchenStaff) {
    sqlExecuteUpdate("INSERT INTO KitchenStaff VALUES('" + kitchenStaff.getUsername() + "', '"
        + kitchenStaff.getPassword() + "', '" + kitchenStaff.getHostname() + "')");
  }

  /**
   * Saves kitchen staff information to the KithenStaffLogin tables based on user.
   *
   * @param kitchenStaff takes argument of the user (kitchenStaff)
   */

  public void saveKitchenStaffLogin(KitchenStaff kitchenStaff) {
    sqlExecuteUpdate("INSERT INTO KitchenStaffLogin VALUES('" + kitchenStaff.getUsername() + "', '"
        + kitchenStaff.getPassword() + "', '" + kitchenStaff.getHostname() + "')");
  }

  /**
   * Saves manager information to manager table based on the user.
   *
   * @param manager takes argument of the manager user
   */
  
  public void saveManager(Manager manager) {
    sqlExecuteUpdate("INSERT INTO Manager VALUES('" + manager.getUsername() + "', '"
        + manager.getPassword() + "', '" + manager.getHostname() + "')");
  }

  /**
   * Saves manager information to manager login table based on the user.
   *
   * @param manager takes argument of the manager user
   */
  
  public void saveManagerLogin(Manager manager) {
    sqlExecuteUpdate("INSERT INTO ManagerLogin VALUES('" + manager.getUsername() + "', '"
        + manager.getPassword() + "', '" + manager.getHostname() + "')");
  }

  /**
   * This method gets the hostname from a user table.
   *
   * @param sql query defining where we want to get the hostname from.
   * @return hostname of users
   */
  
  private String readUserHostname(String sql) {
    String hostname = "";
    try {
      Statement statement = connection.createStatement();
      ResultSet result = statement.executeQuery(sql);
      while (result.next()) {
        hostname = result.getString("hostname");
      }
      statement.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return hostname;

  }

  /**
   * Gets the hostname of a waiter assigned to a table.
   *
   * @param tableNumber which a waiter we want the hostname of is assigned to
   * @return the hostname of that waiter
   */
  
  public String readWaiterHostname(String tableNumber) {
    return readUserHostname(
        "SELECT hostname FROM Waiter WHERE tableNumber = " + "'" + tableNumber + "'");
  }

  /**
   * Sets the table number to a specific waiter dependent on their host name.
   *
   * @param hostname    specific hostname for a waiter
   * @param tableNumber table number which we want to assign the waiter
   */
  
  public void setWaiterTableNumber(String hostname, int tableNumber) {
    sqlExecuteUpdate("UPDATE Waiter SET tableNumber = '" + tableNumber + "' WHERE hostname = '"
        + hostname + "'");
  }

  /**
   * Sets the customer to a specific table dependent on their host name.
   *
   * @param hostname    host name of the customer
   * @param tableNumber table we want to assign customer
   */
  
  public void setCustomerTableNumber(String hostname, int tableNumber) {
    sqlExecuteUpdate("UPDATE Customer SET tableNumber = '" + tableNumber + "' WHERE hostname = '"
        + hostname + "'");
  }

  /**
   * Sets the order number to a specific customer.
   *
   * @param hostname    hostname of a specific customer
   * @param orderNumber order number we want to set to the customer
   */
  
  public void setCustomerOrderNumber(String hostname, int orderNumber) {
    sqlExecuteUpdate("UPDATE Customer SET orderNumber = '" + orderNumber + "' WHERE hostname = '"
        + hostname + "'");
  }

  /**
   * Sets the a customer and a waiter to a specific table.
   *
   * @param customer    hostname of customer
   * @param waiter      hostname of waiter
   * @param tableNumber table we want to set
   */
  
  public void setTableCustomerWaiter(String customer, String waiter, String tableNumber) {
    sqlExecuteUpdate("UPDATE tables SET waiter = '" + waiter + "' WHERE number = '"
        + Integer.parseInt(tableNumber) + "'");
  }

  /**
   * Gets the host name of the customer dependent on what table they are at.
   *
   * @param tableNumber table number assigned to specific customer
   * @return host name of the customer
   */
  
  public String readCustomerHostname(String tableNumber) {
    return readUserHostname(
        "SELECT hostname FROM Customer WHERE tableNumber = " + "'" + tableNumber + "'");
  }

  /**
   * Gets the ordernumber of a customer specified by their hostname.
   *
   * @param hostname hostname of the specific customer
   * @return ordernumber of the specificed customer
   */
  
  public int readCustomerOrderNumber(String hostname) {
    int orderNumber = -1;
    try {
      Statement s = connection.createStatement();
      String query = "SELECT OrderNumber FROM Customer WHERE hostname = " + "'" + hostname + "'";
      ResultSet result = s.executeQuery(query);
      while (result.next()) {
        orderNumber = result.getInt("OrderNumber");
      }
      s.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return orderNumber;
  }

  /**
   * Returns a list of active customers and gets the username and passwords
   * assigned to each customer.
   *
   * @return customer creates a list of customers with their information.
   */
  
  public ObservableList<Customer> customerList() {
    ObservableList<Customer> customers = FXCollections.observableArrayList();
    try {
      Statement s = connection.createStatement();
      String query = "SELECT * FROM Customer";
      ResultSet rs = s.executeQuery(query);
      while (rs.next()) {
        String username = rs.getString("username");
        String password = rs.getString("password");
        String hostname = rs.getString("hostname");
        int tableNumber = rs.getInt("tableNumber");
        int orderNumber = rs.getInt("orderNumber");
        Customer customer = new Customer(username, password, hostname, tableNumber, orderNumber);
        customers.add(customer);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return customers;
  }

  /**
   * Returns list of customers which have a registered account.
   * 
   * @return customer returns a list of customer.
   */

  public ObservableList<Customer> customerLoginList() {
    ObservableList<Customer> customers = FXCollections.observableArrayList();
    try {
      Statement s = connection.createStatement();
      String query = "SELECT * FROM CustomerLogin";
      ResultSet rs = s.executeQuery(query);
      while (rs.next()) {
        String username = rs.getString("username");
        String password = rs.getString("password");
        String hostname = rs.getString("hostname");
        Customer customer = new Customer(username, password, hostname);
        customers.add(customer);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return customers;
  }

  /**
   * Gets the hostname of the kitchen staff.
   * 
   * @param tableNumber number of table (string).
   * @return returns the host name of a kitchen staff.
   */
  
  public String readKitchenStaffHostname(String tableNumber) {
    String hostname = "";
    try {
      Statement s = connection.createStatement();
      String query = "SELECT * FROM KitchenStaff";
      ResultSet result = s.executeQuery(query);
      while (result.next()) {
        hostname = result.getString("hostname");
      }
      s.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return hostname;
  }

  /**
   * removes a specific waiter from the waiter table dependent on their hostname.
   *
   * @param hostname specific hostname of the waiter we are wanting to remove
   */
  
  public void removeWaiter(String hostname) {
    sqlExecuteUpdate("DELETE FROM Waiter WHERE hostname = " + "'" + hostname + "'");
  }

  /**
   * remove customer dependant on host name from the customer table.
   *
   * @param hostname specific host name of the customer we want to remove
   */
  
  public void removeCustomer(String hostname) {
    sqlExecuteUpdate("DELETE FROM Customer WHERE hostname = " + "'" + hostname + "'");
  }

  public void removeManager(String hostname) {
    sqlExecuteUpdate("DELETE FROM Manager WHERE hostname = " + "'" + hostname + "'");
  }

  /**
   * remove kitchen staff dependant on their specific hostname.
   *
   * @param hostname specific hostname of the kitchen staff we want to remove
   */
  
  public void removeKitchenStaff(String hostname) {
    sqlExecuteUpdate("DELETE FROM KitchenStaff WHERE hostname = " + "'" + hostname + "'");
  }

  /**
   * Method which adds all of the foods from each course of the order, in to a
   * single string for use in sql statements.
   *
   * @param sql        query built
   * @param foods      is the course of the order, if empty adds "none", if not it
   *                   appends each food with seperator
   * @param appendNext boolean for adding the comma
   */
  
  private StringBuilder saveOrderAdd(StringBuilder sql, ObservableList<Food> foods,
      boolean appendNext) {
    if (!foods.isEmpty()) {
      for (Food food : foods) {
        sql.append(food.getMeal() + "~");
      }
      sql.deleteCharAt(sql.length() - 1);
    } else {
      sql.append("None");
    }
    if (appendNext) {
      sql.append("','");
    }
    return sql;
  }

  /**
   * Creates an order if there isn't a current order, or updates the current order
   * if there is currently one. Gets an order, converting it into a string,
   * allowing us to compose a query to insert it into the database.
   *
   * @param order order we want to save into the database
   */
  
  public void saveOrder(Order order) {
    try {
      final Statement s = connection.createStatement();
      StringBuilder sql = new StringBuilder();
      if (order.getOrderNumber() == 0) {
        sql.append("'" + order.getTableNumber() + "','");
        saveOrderAdd(sql, order.getStarters(), true);
        saveOrderAdd(sql, order.getMains(), true);
        saveOrderAdd(sql, order.getDesserts(), true);
        saveOrderAdd(sql, order.getDrinks(), true);
        sql.append(order.getTotalCost());
        sql.append("','" + order.getTime());
        sql.append("','" + order.getOrderStatus() + "'");
        String createdLine = "INSERT INTO Orders (TableNumber, Starters, Mains, Desserts, Drinks, totalCost, OrderPlaced, orderStatus) VALUES ("
            + sql.toString() + ")";
        s.execute(createdLine);
      } else {
        StringBuilder starters = saveOrderAdd(new StringBuilder(), order.getStarters(), false);
        StringBuilder mains = saveOrderAdd(new StringBuilder(), order.getMains(), false);
        StringBuilder desserts = saveOrderAdd(new StringBuilder(), order.getDesserts(), false);
        StringBuilder drinks = saveOrderAdd(new StringBuilder(), order.getDrinks(), false);
        String query = "UPDATE Orders SET tableNumber = '" + order.getTableNumber()
            + "', Starters = '" + starters.toString() + "', Mains = '" + mains.toString()
            + "', Desserts = '" + desserts.toString() + "', Drinks = '" + drinks.toString()
            + "', totalCost = '" + order.getTotalCost() + "', OrderPlaced = '" + order.getTime()
            + "', orderStatus = '" + order.getOrderStatus() + "' WHERE OrderNumber = '"
            + order.getOrderNumber() + "'";
        s.executeUpdate(query);
      }
      s.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Removes an order from the database dependent on the order number.
   *
   * @param orderNumber the order number to remove the order from
   * @return whether removing the order has been successful or not
   */
  
  public boolean removeOrder(int orderNumber) {
    try {
      Statement s = connection.createStatement();
      String query = "DELETE FROM orders WHERE OrderNumber = " + orderNumber;
      s.executeUpdate(query);
      s.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Returns an order saved on the database.
   *
   * @param sql query specifying the order
   * @return order object containing information about that order.
   */
  
  private Order readAnyOrder(String sql) {
    Order order = null;
    try {
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        order = new Order(rs.getInt("OrderNumber"), rs.getInt("TableNumber"),
            rs.getString("Starters"), rs.getString("Mains"), rs.getString("Desserts"),
            rs.getString("Drinks"), rs.getDouble("totalCost"), rs.getString("orderPlaced"),
            rs.getString("orderStatus"));
      }
      s.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return order;

  }

  /**
   * Reads all orders under a specified order number.
   *
   * @param orderNumber order number of orders we want to get
   * @return order object containing the order information of a specific order
   *         number
   */
  
  public Order readOrder(int orderNumber) {
    return readAnyOrder("SELECT * FROM Orders WHERE OrderNumber = " + orderNumber);
  }

  /**
   * Reads last order based on time, returning it in an order object.
   *
   * @param time time order was placed
   * @return order object containing the information about specified orders based
   *         on the time they were placed
   */
  
  public Order readLastOrder(String time) {
    return readAnyOrder(
        "SELECT * FROM Orders WHERE orderPlaced = E'" + time.replaceAll(":", "\\:") + "'");
  }

  /**
   * Inserts the IP of a user into the IP table.
   *
   * @param username specific user we want to give the IP of
   * @param ip       Ip of that user
   */
  
  public void writeIp(String username, String ip) {
    sqlExecuteUpdate("INSERT INTO Ip (username, ip) VALUES ('" + username + "','" + ip + "')");
  }

  /**
   * Reads IP of a specific user based on their username.
   *
   * @param username specify the user we want the IP of
   * @return Ip of that user
   */
  
  public String readIp(String username) {
    String ip = "";
    try {
      Statement s = connection.createStatement();
      String query = "SELECT username, ip FROM Ip WHERE username = " + "'" + username + "'";
      ResultSet result = s.executeQuery(query);
      while (result.next()) {
        result.getString("username");
        ip = result.getString("ip");
      }
      s.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ip;
  }

  /**
   * removes the IP of a specific user from the Ip table based on users username.
   *
   * @param username specifies user to delete ip of
   */
  
  public void removeIp(String username) {
    sqlExecuteUpdate("DELETE FROM Ip WHERE username = " + "'" + username + "'");
  }

  /**
   * Returns a list of the tables in the cafe as a table object containing
   * information about that table status. If waiter does not equal null it sets
   * the waiter.
   *
   * @return tables Observable list of tables.
   */
  
  public ObservableList<Table> tableList() {
    ObservableList<Table> tables = FXCollections.observableArrayList();
    try {
      Statement s = connection.createStatement();
      String query = "SELECT * FROM tables";
      ResultSet rs = s.executeQuery(query);
      while (rs.next()) {
        Table customerTable = new Table(rs.getInt("Number"), rs.getInt("Seats"));
        String waiter = rs.getString("Waiter");
        if (!waiter.equals("NULL")) {
          Waiter waiterObj = new Waiter(rs.getString("Waiter"));
          customerTable.setWaiter(waiterObj);
        }
        String status = rs.getString("Status").replace(" ", "");
        customerTable.setStatus(TableStatus.valueOf(status.toUpperCase()));
        tables.add(customerTable);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return tables;
  }

  /**
   * Updates the information of a table, assigning it a tablenumber and seats,
   * setting the waiter to null, and setting the status to empty.
   *
   * @param tablenumber table number we want to assign
   * @param tableseats  number of seats at the table
   */
  
  public void updateTable(int tablenumber, int tableseats) {
    sqlExecuteUpdate("INSERT INTO tables " + "(Number, Seats, Waiter, Status) " + "VALUES ( '"
        + tablenumber + "', '" + tableseats + "', '" + null + "', '" + TableStatus.EMPTY + "')");
  }

  /**
   * delete a table from a list of the tables in the cafe dependent on its table
   * number.
   *
   * @param tablenumber tablenumber of table to delete
   */
  
  public void deleteTable(int tablenumber) {
    sqlExecuteUpdate("DELETE FROM tables" + " WHERE Number = '" + tablenumber + "'");
  }

  /**
   * Query to return a list of users currently saved on the waiter table.
   *
   * @return list of saved waiters
   */
  public ObservableList<Waiter> waiterList() {
    ObservableList<Waiter> waiters = FXCollections.observableArrayList();
    try {
      Statement s = connection.createStatement();
      String query = "SELECT * FROM Waiter";
      ResultSet rs = s.executeQuery(query);
      while (rs.next()) {
        Table table = null;
        Waiter waiter = new Waiter(rs.getString("username"), rs.getString("password"),
            rs.getString("hostname"), table, rs.getInt("tableNumber"));
        waiters.add(waiter);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return waiters;
  }

  /**
   * Return list of registered waiters from the waiter login table and creates a
   * waiter object for each case.
   *
   * @return list of registered waiters
   */
  
  public ObservableList<Waiter> waiterLoginList() {
    ObservableList<Waiter> waiters = FXCollections.observableArrayList();
    try {
      Statement s = connection.createStatement();
      String query = "SELECT * FROM WaiterLogin";
      ResultSet rs = s.executeQuery(query);
      while (rs.next()) {
        Waiter waiter = new Waiter(rs.getString("username"), rs.getString("password"),
            rs.getString("hostname"));
        waiters.add(waiter);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return waiters;
  }

  /**
   * Gets all orders from the database, creating new order objects from the
   * results.
   *
   * @return list of orders currently on the Orders table.
   */

  public ObservableList<Order> orderList() {
    ObservableList<Order> orders = FXCollections.observableArrayList();
    try {
      Statement s = connection.createStatement();
      String query = "SELECT * FROM Orders";
      ResultSet rs = s.executeQuery(query);
      while (rs.next()) {
        Order order = new Order(rs.getInt("OrderNumber"), rs.getInt("TableNumber"),
            rs.getString("Starters"), rs.getString("Mains"), rs.getString("Desserts"),
            rs.getString("Drinks"), rs.getDouble("totalCost"), rs.getString("orderPlaced"),
            rs.getString("orderStatus"));
        orders.add(order);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return orders;
  }

  /**
   * returns a list of all active kitchen staff, creating a kitchenStaff object
   * for each case.
   *
   * @return list of active kitchen staff in the kitchen staff
   */
  
  public ObservableList<KitchenStaff> kitchenStaffList() {
    ObservableList<KitchenStaff> kitchenStaff = FXCollections.observableArrayList();
    try {
      Statement s = connection.createStatement();
      String query = "SELECT * FROM KitchenStaff";
      ResultSet rs = s.executeQuery(query);
      while (rs.next()) {
        kitchenStaff.add(new KitchenStaff(rs.getString("username"), rs.getString("password"),
            rs.getString("hostname")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return kitchenStaff;
  }

  /**
   * returns a list of all registered kitchen staff logins, creating a
   * kitchenStaff object out of each case.
   *
   * @return list of registered kitchen staff in the kitchen staff logins
   */
  
  public ObservableList<KitchenStaff> kitchenStaffLoginList() {
    ObservableList<KitchenStaff> kitchenStaff = FXCollections.observableArrayList();
    try {
      Statement s = connection.createStatement();
      String query = "SELECT * FROM KitchenStaffLogin";
      ResultSet rs = s.executeQuery(query);
      while (rs.next()) {
        kitchenStaff.add(new KitchenStaff(rs.getString("username"), rs.getString("password"),
            rs.getString("hostname")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return kitchenStaff;
  }

  /**
   * returns a list of all active managers and crates a manager object for each
   * case.
   *
   * @return list of all active managers present in the managers table
   */
  
  public ObservableList<Manager> managerList() {
    ObservableList<Manager> managers = FXCollections.observableArrayList();
    try {
      Statement s = connection.createStatement();
      String query = "SELECT * FROM Manager";
      ResultSet rs = s.executeQuery(query);
      while (rs.next()) {
        managers.add(new Manager(rs.getString("username"), rs.getString("password"),
            rs.getString("hostname")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return managers;
  }

  /**
   * returns a list of all registered managers, making a manager object for each
   * case.
   *
   * @return list of all registered managers
   */
  
  public ObservableList<Manager> managerLoginList() {
    ObservableList<Manager> managers = FXCollections.observableArrayList();
    try {
      Statement s = connection.createStatement();
      String query = "SELECT * FROM ManagerLogin";
      ResultSet rs = s.executeQuery(query);
      while (rs.next()) {
        managers.add(new Manager(rs.getString("username"), rs.getString("password"),
            rs.getString("hostname")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return managers;
  }

  /**
   * Method for adding food the the table through the SQL statement INSERT INTO.
   *
   * @param tableName       table entry inserted into
   * @param foodItem        name of food item
   * @param foodDescription description of food
   * @param foodType        meal type
   * @param foodNutrition   nutrition of food
   * @param foodAllergies   food allergies
   * @param foodCost        price of food
   * @param cookingtime     time to prepare food
   * @param URL             food image url
   */
  
  public void updateMenu(String tableName, String foodItem, String foodDescription, String foodType,
      String foodNutrition, String foodAllergies, String foodCost, String cookingtime, String URL) {
    sqlExecuteUpdate("INSERT INTO " + tableName
        + "(Meal, Meal_Description, Meal_Type, Nutritional_Info, Allergy_Info, Cost, Cooking_Time) "
        + "VALUES ( '" + foodItem + "', '" + foodDescription + "', '" + foodType.toUpperCase()
        + "', '" + foodNutrition + "', '" + foodAllergies + "', '" + foodCost + "', '" + cookingtime
        + "', '" + URL + "')");
  }

  /**
   * Method for deleting a named food item from the menu.
   *
   * @param tableName table food is in
   * @param foodItem  food item to remove
   */
  
  public void deleteMenu(String tableName, String foodItem) {
    sqlExecuteUpdate("DELETE FROM " + tableName + " WHERE Meal = '" + foodItem + "'");
  }

  /**
   * Query allowing the user to see all foods on the menu, and ordering and type
   * them dependent on the query. This method also provides filtering of the menu.
   *
   * @param queryType String of food type we want to get the meals from
   * @return returns list of food objects on the database
   */
  
  public ObservableList<Food> getMenu(String queryType) {
    ObservableList<Food> foods = FXCollections.observableArrayList();
    String query = "";
    if (queryType.equals("entire")) {
      query = "SELECT * FROM Food ORDER BY CASE" + " WHEN Meal_Type = 'Starter' THEN 1"
          + " WHEN Meal_Type = 'Main' THEN 2" + " WHEN Meal_Type = 'Dessert' THEN 3"
          + " WHEN Meal_Type = 'Drink' THEN 4" + " END";
    } else if (queryType.equals("drinks")) {
      query = "SELECT * FROM Food WHERE Meal_Type = 'Drink' " + "ORDER BY Nutritional_info ASC";
    } else if (queryType.equals("starters")) {
      query = "SELECT * FROM Food WHERE Meal_Type = 'Starter' " + "ORDER BY Nutritional_info ASC";
    } else if (queryType.equals("mains")) {
      query = "SELECT * FROM Food WHERE Meal_Type = 'Main' " + "ORDER BY Nutritional_info ASC";
    } else if (queryType.equals("desserts")) {
      query = "SELECT * FROM Food WHERE Meal_Type = 'Dessert' " + "ORDER BY Nutritional_info ASC";
    } else if (queryType.equals("low_cal")) {
      query = "SELECT * FROM Food WHERE Nutritional_info < '401' ORDER BY CASE "
          + " WHEN Meal_Type = 'Starter' THEN 1" + " WHEN Meal_Type = 'Main' THEN 2"
          + " WHEN Meal_Type = 'Dessert' THEN 3" + " WHEN Meal_Type = 'Drink' THEN 4" + " END";
    } else if (queryType.equals("savers")) {
      query = "SELECT * FROM Food WHERE LEFT(Cost,2) < '7' AND LENGTH(Cost) < 5 ORDER BY CASE "
          + " WHEN Meal_Type = 'Starter' THEN 1" + " WHEN Meal_Type = 'Main' THEN 2"
          + " WHEN Meal_Type = 'Dessert' THEN 3" + " WHEN Meal_Type = 'Drink' THEN 4" + " END";
    }
    try {
      Statement s = connection.createStatement();
      ResultSet result = s.executeQuery(query);
      while (result.next()) {
        String m = result.getString("Meal");
        String md = result.getString("Meal_description");
        String ni = result.getString("Nutritional_info");
        String mt = result.getString("Meal_Type");
        String ai = result.getString("Allergy_info");
        String cs = result.getString("Cost");
        String time = result.getString("Cooking_Time");
        String url = result.getString("food_image");
        foods.add(new Food(m, md, MealType.fromString(mt), ni, ai, Double.parseDouble(cs),
            Double.parseDouble(time), url));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return foods;
  }

  /**
   * returns a list of tables names from pg_tables, where the schma name is set to
   * public.
   *
   * @return list of table names
   */
  
  public ObservableList<String> allTablesName() {
    ObservableList<String> tables = FXCollections.observableArrayList();
    String query = "SELECT tablename FROM pg_tables WHERE schemaname = 'public'";
    try {
      Statement s = connection.createStatement();
      ResultSet result = s.executeQuery(query);

      while (result.next()) {
        tables.add(result.getString("tablename"));
      }
      s.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return tables;
  }

  /**
   * Inserting in to the table though an array of values.
   *
   * @param table  table we want to insert into
   * @param values values we wish to insert
   */
  
  public void insertIntoTable(String table, String[] values) {
    if (values.length != 0) {
      String insertion = "INSERT INTO " + table + " VALUES(";
      try {
        Statement s = connection.createStatement();
        for (int i = 0; i < values.length - 1; i++) {
          insertion += "'" + values[i] + "', ";
        }
        insertion += "'" + values[values.length - 1] + "')";
        s.execute(insertion);
        s.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Gets the meal price of a specified meal.
   *
   * @param foodName meal we wish to get price of
   * @return cost assigned to that meal
   */
  
  public double getMealPrice(String foodName) {
    double cost = 0;
    String query = "SELECT Cost FROM Food " + "WHERE Meal = '" + foodName + "'";
    try {
      Statement s = connection.createStatement();
      ResultSet result = s.executeQuery(query);
      while (result.next()) {
        cost = Float.parseFloat(result.getString("Cost"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return cost;
  }

  /**
   * Assigns a new price to a food item on the menu by executing a query.
   *
   * @param foodName name of the food we want reassign a price too
   * @param f        price we want to assign to a meal
   */
  
  public void updatePrice(String foodName, double f) {
    sqlExecuteUpdate(
        "UPDATE Food " + "SET cost = '" + f + "' " + "WHERE Meal = '" + foodName + "'");
  }

}