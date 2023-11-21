package cafe;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class is used to obtain information about the maven build profile that is currently being
 * used.
 *
 * @author thanushen
 */

public class Config {

  private static final Properties properties = new Properties();
  private static final InputStream inputStream = Main.class
      .getResourceAsStream("/config.properties");

  private Config() {

  }

  /**
   * This obtains the maven build profile name by loading the Properties.properties file.
   *
   * @return A string with the name of the build profile.
   */

  public static String getUser() {
    String user = "";
    try {
      properties.load(inputStream);
      user = properties.getProperty("user");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return user;
  }

  /**
   * This obtains the version number by loading the Properties.properties file.
   *
   * @return A string with the version number.
   */

  public static String getVersion() {
    String version = "";
    try {
      properties.load(inputStream);
      version = properties.getProperty("version");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return version;
  }
}
