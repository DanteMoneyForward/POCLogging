package com.example.pocloggingmaskwithlayoutpattern.logging.converter;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LoggingInitializer {

  @Value("${log.sensitive.patterns}")
  private String patternsConfig;

  @PostConstruct
  public void init() {
    List<String> patterns = Arrays.asList(patternsConfig.split(","));
    MaskedConverter.setMaskPatterns(patterns);
  }
}
