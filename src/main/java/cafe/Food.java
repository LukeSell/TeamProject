package cafe;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class to represent the food object.
 */

public class Food {

  private String meal;
  private String mealDescriptor;
  private MealType mealType;
  private double price;
  private ArrayList<String> nutrition;
  private ArrayList<String> warnings;
  private double cookingtime;
  private String url;

  /**
   * Initialises a food object.
   *
   * @param meal meal name
   * @param mealDescriptor meal description
   * @param mealType meal type
   * @param nutrition nutritional information
   * @param warnings warnings such as allergies
   * @param price price of individual food
   * @param cookingtime time to cook food
   * @param url url for image of that food
   */

  public Food(String meal, String mealDescriptor, MealType mealType, String nutrition,
      String warnings, double price, double cookingtime, String url) {
    this.meal = meal;
    this.mealDescriptor = mealDescriptor;
    this.mealType = mealType;
    this.nutrition = new ArrayList<>();
    addNutrition(nutrition);
    this.warnings = new ArrayList<>();
    addWarning(warnings);
    this.price = price;
    this.cookingtime = cookingtime;
    this.url = url;
  }

  /**
   * Returns meal name.
   *
   * @return meal name in string
   */

  public String getMeal() {
    return meal;
  }

  /**
   * Returns the meal name in hash code.
   *
   * @return meal name hash code
   */

  @Override
  public int hashCode() {
    return Objects.hash(meal);
  }

  /**
   * Checks if two objects are equal.
   *
   * @param obj object to compare it to
   * @return boolean if they equal or not
   */

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Food other = (Food) obj;
    return Objects.equals(meal, other.meal);
  }

  /**
   * Returns url of image of food.
   *
   * @return string with url
   */

  public String getURL() {
    return url;
  }

  /**
   * Sets food name.
   *
   * @param meal food name
   */

  public void setMeal(String meal) {
    this.meal = meal;
  }

  /**
   * Returns all nutritional information in the form of string array.
   * @return nutrition nutritional information of the food
   */

  public ArrayList<String> getNutrition() {
    return nutrition;
  }

  /**
   * Returns all warning information in the form of string array.
   * @return warnings allergy warnings of the food
   */


  public ArrayList<String> getWarnings() {
    return warnings;
  }

  /**
   * Set food description.
   *
   * @param mealDescriptor meal description
   */

  public void setMealDescriptor(String mealDescriptor) {
    this.mealDescriptor = mealDescriptor;
  }

  /**
   * Set type of meal.
   *
   * @param mealType type of meal
   */

  public void setMealType(MealType mealType) {
    this.mealType = mealType;
  }

  /**
   * Returns type of meal.
   *
   * @return meal type
   */

  public MealType getMealType() {
    return mealType;
  }

  /**
   * Returns meal description.
   *
   * @return string with meal description
   */

  public String getMealDescriptor() {
    return mealDescriptor;
  }

  /**
   * Adds nutritional information.
   * @param nutritionInfo nutritional information of the food
   */
  public void addNutrition(String nutritionInfo) {
    nutrition.add(nutritionInfo);
  }

  /**
   * Adds warning information to food.
   *
   * @param warningInfo warning string
   */

  public void addWarning(String warningInfo) {
    warnings.add(warningInfo);
  }

  /**
   * Returns all warnings to a single string.
   *
   * @return String with all warnings
   */

  public String warningToString() {
    String warningsString = "";
    for (int i = 0; i < warnings.size() - 1; i++) {
      warningsString += warnings.get(i) + ", ";
    }
    warningsString += warnings.get(warnings.size() - 1);
    return warningsString;
  }

  /**
   * Returns all nutritional info to a single string.
   *
   * @return String with all nutritional info
   */

  public String nutritionToString() {
    String nutritionString = "";
    for (int i = 0; i < nutrition.size() - 1; i++) {
      nutritionString += nutrition.get(i) + ", ";
    }
    nutritionString += nutrition.get(nutrition.size() - 1);
    return nutritionString;
  }

  /**
   * returns price of the single food.
   *
   * @return double of price.
   */

  public double getPrice() {
    return price;
  }

  /**
   * Sets the price of the food.
   *
   * @param price price of food
   */

  public void setPrice(long price) {
    this.price = price;
  }

  /**
   * Gets cooking time of food.
   *
   * @return cooking time
   */

  public double getTime() {
    return cookingtime;
  }


  /**
   * Sets the cooking time of the food.
   *
   * @param cookingtime cooking time of food
   */

  public void setTime(int cookingtime) {
    this.cookingtime = cookingtime;
  }

  @Override
  public String toString() {
    return meal;
  }

  public String toLongString() {
    return "Meal: " + meal + " Meal Type: " + mealType + "\nDescription: " + mealDescriptor
        + "\nPrice: £" + price + "0" + "\nNutrition: " + nutritionToString() + "\nWarnings: "
        + warningToString() + "\nPreparation Time: " + cookingtime + " Minutes";
  }
}
