package cafe;

/**
 * Enumeration to store the different types of meal.
 */

public enum MealType {

  DRINK("Drink"),
  STARTER("Starter"),
  MAIN("Main"),
  DESSERT("Dessert"),
  NONE("None");

  private String description;

  MealType(String description) {
    this.description = description;
  }

  public static MealType fromString(String string) {
    for (MealType mt : MealType.values()) {
      if (mt.toString().equals(string)) {
        return mt;
      }
    }
    return MealType.NONE;
  }

  @Override
  public String toString() {
    return description;
  }
}