package dev.binhcn.config.test;

import java.util.regex.*;
class RegexExample3{
  public static void main(String args[]){
    Pattern pattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}-\\d{1,3}");
    Matcher matcher = pattern.matcher("payment-engine/cashier/loadtest/public/1.2.3-4/stable 25-09-1984");
    String group = null;
    if (matcher.find()) {
      group = matcher.group();
    }
  }
}