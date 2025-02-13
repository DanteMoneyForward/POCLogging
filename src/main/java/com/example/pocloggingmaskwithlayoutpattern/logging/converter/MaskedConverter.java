package com.example.pocloggingmaskwithlayoutpattern.logging.converter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;
import com.example.pocloggingmaskwithlayoutpattern.logging.utils.LoggingUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskedConverter extends CompositeConverter<ILoggingEvent> {
  private static final List<String> maskPatterns = new CopyOnWriteArrayList<>();;
  private static Pattern compiledPattern = Pattern.compile(""); // Default empty pattern
  private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

  // Dynamically updates patterns (ONLY when needed)
  public static void setMaskPatterns(List<String> patterns) {
    lock.writeLock().lock();
    try {
      if (!patterns.equals(maskPatterns)) { // Only update if patterns actually change
        maskPatterns.clear();
        maskPatterns.addAll(patterns);
        compiledPattern = Pattern.compile(String.join("|", maskPatterns), Pattern.MULTILINE);
      }
    } finally {
      lock.writeLock().unlock();
    }
  }

  @Override
  public String transform(ILoggingEvent iLoggingEvent, String message) {
    return masking(message);
  }

  private String masking(String message) {
    if (!message.contains("=")) return message; // 🚀 Quick check to skip unnecessary regex

    lock.readLock().lock();
    try {
      Matcher matcher = compiledPattern.matcher(message);
      StringBuffer sb = new StringBuffer();

      while (matcher.find()) {
        matcher.appendReplacement(sb, maskSensitive(matcher.group()));
      }
      matcher.appendTail(sb);
      return sb.toString();
    } finally {
      lock.readLock().unlock();
    }
  }

  private String maskSensitive(String matchedText) {
    String[] split = matchedText.split("=");
    if (split.length < 2) return matchedText; // Avoids error

    String maskedValue = LoggingUtils.getMaskedText(split[1]);
    return split[0] + "=" + maskedValue;
  }
}
