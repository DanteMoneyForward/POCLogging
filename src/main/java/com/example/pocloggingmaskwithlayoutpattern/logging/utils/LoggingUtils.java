package com.example.pocloggingmaskwithlayoutpattern.logging.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LoggingUtils {
  public static String getMaskedText(String pan) {
    return "******" + pan.substring(6);
  }
}
