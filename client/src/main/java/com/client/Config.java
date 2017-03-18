package com.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
  @Bean
  @RefreshScope
  public Data bean(@Value("${testKey}") String value) {
    return new Data(value);
  }
}
