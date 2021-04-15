package dev.binhcn.util;

import java.security.SecureRandom;

public class NameGenerator {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";

    private static final String NAME_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER;

  private static final String[] YEAR_LIST = new String[] {"20", "21", "22"};
  private static final String[] MONTH_LIST =
      new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    private static SecureRandom random = new SecureRandom();

    public static String randomName(int length) {
      if (length < 1) {
        throw new IllegalArgumentException();
      }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(NAME_ALLOW_BASE.length());
            char rndChar = NAME_ALLOW_BASE.charAt(rndCharAt);
            sb.append(rndChar);
        }

        return sb.toString();
    }

  public static String randomNumber(int length) {
    if (length < 1) {
      throw new IllegalArgumentException();
    }

    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int rndCharAt = random.nextInt(NUMBER.length());
      char rndChar = NUMBER.charAt(rndCharAt);
      sb.append(rndChar);
    }
    return sb.toString();
  }

  public static String randomTransId() {
    StringBuilder sb = new StringBuilder(15);
    int index = random.nextInt(YEAR_LIST.length);
    sb.append(YEAR_LIST[index]);
    index = random.nextInt(MONTH_LIST.length);
    sb.append(MONTH_LIST[index]);
    sb.append(randomNumber(11));
    return sb.toString();
  }

}