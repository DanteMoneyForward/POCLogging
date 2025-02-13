package com.example.pocloggingmaskwithlayoutpattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Slf4j
public class PocLoggingMaskWithLayoutPatternApplication {

  public static void main(String[] args) {
    SpringApplication.run(PocLoggingMaskWithLayoutPatternApplication.class, args);
  }

  @GetMapping("/hello/{logStr}")
  public String hello(@PathVariable String logStr) {
    log.info("This is a test log: " + logStr);
    return "Hello " + logStr;
  }
}
