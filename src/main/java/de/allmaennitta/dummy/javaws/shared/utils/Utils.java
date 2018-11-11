package de.allmaennitta.dummy.javaws.shared.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public final class Utils {

  private static final String DELIMITER = ":";

  @SuppressWarnings("SpellCheckingInspection")
  public static Duration hhmmToDuration(int hh, int mm) {
    return Duration.ofHours(hh).plusMinutes(mm);
  }

  /**
   * Method to transform java8-duration into a well formatted duration string //TODO probably obsolete as soon as we use
   * Java9
   *
   * @param duration as used in java8
   * @return String of the form "hh:mm"
   */
  public static String durationToString(Duration duration) {
    List hoursMinutes = Utils.durationToHHmm(duration);
    String formatString = "%02d" + DELIMITER + "%02d";
    return String.format(formatString, hoursMinutes.get(0), hoursMinutes.get(1));
  }

  /**
   * Method to transform String of the form "hh:mm" into java8-duration //TODO probably obsolete as soon as we use Java9
   *
   * @param duration-String of the form "hh:mm"
   * @return java8-duration
   */
  public static Duration stringToDuration(String duration) {
    StringTokenizer tokenizer = new StringTokenizer(duration, DELIMITER);

    if (tokenizer.countTokens() != 2) {
      throw new IllegalArgumentException("Duration doesn't have the format " +
          "'hh:mm':" + duration);
    }
    return Duration.of(Integer.valueOf(tokenizer.nextToken()), ChronoUnit.HOURS)
        .plus(Duration.of(Integer.valueOf(tokenizer.nextToken()), ChronoUnit.MINUTES));
  }

  /**
   * Convenience converter to transform java8-duration into list of hour/minute-integers
   *
   * @param duration as used in java8
   * @return List of hours and minutes as integer
   */
  private static List<Integer> durationToHHmm(Duration duration) {
    int hours = (int) duration.toHours();
    int minutes = (int) ((duration.getSeconds() % (60 * 60)) / 60);
    return Arrays.asList(hours, minutes);
  }

  /**
   * @deprecated please use - stringFromFileInTargetRoot(String filename) or - stringFromFile(URL dirURL, String filename)
   */
  @Deprecated
  public static String readFileAsSingleString(String filename) throws MalformedURLException {
    return stringFromFileInTargetRoot(filename);
  }

  /**
   * Returns the complete content of a file (in target-root) as a single string
   *
   * @param filename of a file in the classpath
   * @return String of the content
   */
  public static String stringFromFileInTargetRoot(String filename) throws MalformedURLException {
    URL resourceURL = Utils.class.getClassLoader().getResource(filename);
    return stringFromFile(resourceURL, filename);
  }

  /**
   * Returns the complete content of a file as a single string
   *
   * @param filename of a file in the desired directory URL
   * @return String of the content
   */
  public static String stringFromFile(URL dirURL, String filename) throws MalformedURLException {
    URL resourceURL = new URL(dirURL.toString() + "./" + filename);
    if (resourceURL == null) {
      throw new IllegalArgumentException("Filename not valid: " + filename);
    } else {
      try {
        File file = new File(resourceURL.toURI());
        return (String) Files.readAllLines(file.toPath()).stream().reduce("", String::concat);
      } catch (URISyntaxException | IOException var3) {
        throw new IllegalStateException(var3);
      }
    }
  }
}
