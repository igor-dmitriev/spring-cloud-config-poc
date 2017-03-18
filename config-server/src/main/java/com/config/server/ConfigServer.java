package com.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
@EnableAutoConfiguration
@EnableConfigServer
@EnableScheduling
@Import(CustomEnvironmentRepositoryConfiguration.class)
public class ConfigServer {
  public static void main(String[] args) {
    SpringApplication.run(ConfigServer.class, args);
  }

  @Bean
  public Executor taskExecutor() {
    return new ScheduledThreadPoolExecutor(1);
  }

}
