package cafe;

/**
 * Enumerated class of Table use.
 *
 * @author Luke Sell
 * 
 * @see Table
 */

public enum TableStatus {

  EMPTY("Empty"),
  SERVING("Serving"),
  CLEANING("Cleaning"),
  RESERVED("Reserved");

  private String description;

  TableStatus(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return description;
  }
}
