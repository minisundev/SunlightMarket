package com.raincloud.sunlightmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SunlightMarketApplication {

  public static void main(String[] args) {
    SpringApplication.run(SunlightMarketApplication.class, args);
  }

}
