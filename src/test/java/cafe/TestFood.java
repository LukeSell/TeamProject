package cafe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the food object class These tests initially were developed to test
 * the initial implementations of methods, however after further development
 * these tests became redundant, hence why are commented.
 *
 */
public class TestFood {

  Food food;

  @Before
  public void setup() {
    food = new Food("NameOfFood", "description of food", MealType.MAIN, "test", "test", 99.99, 15,
        "test");
  }

  @Test
  public void test_getMeal() {
    assertEquals("NameOfFood", food.getMeal());
  }

  @Test
  public void test_setMeal() {
    food.setMeal("NameOfFoodChanged");
    assertEquals("NameOfFoodChanged", food.getMeal());
  }

  @Test
  public void test_nutritionToString() {
    assertEquals("test", food.nutritionToString());
  }

  @Test
  public void test_warningToString() {
    assertEquals("test", food.warningToString());
  }

  @Test
  public void test_getNutrition() {
    assertEquals("test", food.getNutrition().get(0));
  }

  @Test
  public void test_getWarning() {
    assertEquals("test", food.getWarnings().get(0));
  }

  @Test
  public void test_getMealDescriptor() {
    assertEquals("description of food", food.getMealDescriptor());
  }

  @Test
  public void test_setMealDescriptor() {
    food.setMealDescriptor("Test");
    assertEquals("Test", food.getMealDescriptor());
  }

  @Test
  public void test_getMealType() {
    assertEquals(MealType.MAIN, food.getMealType());
  }

  @Test
  public void test_setMealType() {
    food.setMealType(MealType.DESSERT);
    assertEquals(MealType.DESSERT, food.getMealType());
  }

  @Test
  public void test_getPrice() {
    assertEquals(food.getPrice(), 99.99, 0.0001);
  }

  @Test
  public void test_setPrice() {
    food.setPrice(10);
    assertTrue(food.getPrice() == 10);
  }

  @Test
  public void test_getCookingTime() {
    assertTrue(15 == food.getTime());
  }

  @Test
  public void test_getURL() {
    assertTrue(food.getURL().equals("test"));
  }

}