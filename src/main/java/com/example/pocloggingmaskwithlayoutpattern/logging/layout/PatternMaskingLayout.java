package com.example.pocloggingmaskwithlayoutpattern.logging.layout;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.example.pocloggingmaskwithlayoutpattern.logging.utils.LoggingUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PatternMaskingLayout extends PatternLayout {
  private Pattern multilinePattern;
  private final List<String> maskPatterns = new ArrayList<>();


  public void addMaskPattern(String maskPattern) { //load masked pattern in logback-spring.xml
    maskPatterns.add(maskPattern);
    multilinePattern = Pattern.compile(
            String.join("|", maskPatterns),
            Pattern.MULTILINE
    );
  }

  @Override
  public String doLayout(ILoggingEvent event) {
    return masking(super.doLayout(event));
  }

  private String masking(String message) {
    if (multilinePattern == null) {
      return message;
    }
    StringBuilder sb = new StringBuilder(message);
    Matcher matcher = multilinePattern.matcher(sb);
    while (matcher.find()) {
      maskSensitive(sb, matcher);
    }
    return sb.toString();
  }

  private void maskSensitive(StringBuilder sb, Matcher matcher) {
    String targetExpression = matcher.group();
    String[] split = targetExpression.split("=");
    String pan = split[1];
    String maskedPan = LoggingUtils.getMaskedText(pan);
    int start = matcher.start() + split[0].length() + 1;
    int end = matcher.end();
    sb.replace(start, end, maskedPan);
  }
}
