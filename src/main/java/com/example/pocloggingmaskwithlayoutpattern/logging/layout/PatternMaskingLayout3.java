package com.example.pocloggingmaskwithlayoutpattern.logging.layout;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.PatternLayoutBase;
import com.example.pocloggingmaskwithlayoutpattern.logging.converter.MaskedConverter;

import java.util.Map;

public class PatternMaskingLayout3 extends PatternLayoutBase<ILoggingEvent> {
  @Override
  public Map<String, String> getDefaultConverterMap() {
    Map<String, String> defaultMap = PatternLayout.defaultConverterMap;
    defaultMap.put("mask", MaskedConverter.class.getName());
    return defaultMap;
  }

  @Override
  public String doLayout(ILoggingEvent event) {
    return super.writeLoopOnConverters(event);
  }
}
