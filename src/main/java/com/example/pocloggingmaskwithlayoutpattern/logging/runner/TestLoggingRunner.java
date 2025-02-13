package com.example.pocloggingmaskwithlayoutpattern.logging.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestLoggingRunner implements CommandLineRunner {

  @Override
  public void run(String... args) {
    log.info("This is a email=datneaddd@gmail.com, address=20NguyenCHanh test log");
    log.info("This is aemail=datneaddd@gmail.com test log");
    log.info("This is aaddress=20NguyenCHanh test log");
  }
}