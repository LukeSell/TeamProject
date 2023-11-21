package cafe;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the user object. As this class is inherited by the Manager, KitchenStaff, Customer and
 * Manager class, there will not be any tests for those objects.
 */

public class TestUser {

  User user;

  @Before
  public void setup() {
    user = new User("Test", "Test", "Test", UserRole.CUSTOMER);
  }

  @Test
  public void test_getUsername() {
    Assert.assertEquals("Test", user.getUsername());
  }

  @Test
  public void test_getHostname() {
    Assert.assertEquals("Test", user.getHostname());
  }

  @Test
  public void test_getPassword() {
    Assert.assertEquals("Test", user.getPassword());
  }

  @Test
  public void test_getRole() {
    Assert.assertEquals(UserRole.CUSTOMER, user.getRole());
  }

}
