//package com.example.pocloggingmaskwithlayoutpattern.logging.layout;
//
//import ch.qos.logback.classic.PatternLayout;
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import ch.qos.logback.core.pattern.PatternLayoutBase;
//import com.example.pocloggingmaskwithlayoutpattern.logging.utils.LoggingUtils;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Component
//public class PatternMaskingLayout2 extends PatternLayoutBase<ILoggingEvent> {
//  private Pattern multilinePattern;
//  private final List<String> maskPatterns = new ArrayList<>();
//
//  @Override
//  public void start() {
//    if (!maskPatterns.isEmpty()) {
//      multilinePattern = Pattern.compile(
//              String.join("|", maskPatterns),
//              Pattern.MULTILINE
//      );
//    }
//    super.start(); // Required for Logback to function
//  }
//
//  public void addMaskPattern(String maskPattern) {
//    maskPatterns.add(maskPattern);
//  }
//
//  @Override
//  public Map<String, String> getDefaultConverterMap() {
//    Map<String, String> defaultConverterMap = PatternLayout.defaultConverterMap;
//    return defaultConverterMap;
//  }
//
//  @Override
//  public String doLayout(ILoggingEvent event) {
//    String message = super.writeLoopOnConverters(event);
//    return multilinePattern != null ? masking(message) : message;
//  }
//
//  private String masking(String message) {
//    if (!message.contains("=")) return message; // 🚀 Quick check to skip unnecessary regex
//
//    Matcher matcher = multilinePattern.matcher(message);
//    StringBuffer sb = new StringBuffer();
//
//    while (matcher.find()) {
//      matcher.appendReplacement(sb, maskSensitive(matcher.group()));
//    }
//    matcher.appendTail(sb);
//
//    return sb.toString();
//  }
//
//  private String maskSensitive(String matchedText) {
//    String[] split = matchedText.split("=");
//    if (split.length < 2) return matchedText; // Avoids error
//
//    String maskedValue = LoggingUtils.getMaskedText(split[1]);
//    return split[0] + "=" + maskedValue;
//  }
//}
