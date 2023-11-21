package cafe;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit tests for the user factory class.
 */

public class TestUserFactory {


  @Test
  public void test_getWaiterLogin() {
    User uLogin = UserFactory.getWaiterLogin("JUNIT", "JUNIT");
    Assert.assertNotNull(uLogin);
  }

  @Test
  public void test_getManagerLogin() {
    User uLogin = UserFactory.getManagerLogin("JUNIT", "JUNIT");
    Assert.assertNotNull(uLogin);
  }

  @Test
  public void test_getKitchenStaffLogin() {
    User uLogin = UserFactory.getKitchenStaffLogin("JUNIT", "JUNIT");
    Assert.assertNotNull(uLogin);
  }

  @Test
  public void test_getCustomerLogin() {
    User uLogin = UserFactory.getCustomerLogin("JUNIT", "JUNIT");
    Assert.assertNotNull(uLogin);
  }

}
