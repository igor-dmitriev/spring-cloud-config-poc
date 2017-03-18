package com.config.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.cloud.config.server.environment.NativeEnvironmentRepository;
import org.springframework.cloud.config.server.environment.SearchPathLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
@Profile("custom")
public class CustomEnvironmentRepositoryConfiguration {
  @Autowired
  private ConfigurableEnvironment environment;

  @Bean
  public SearchPathLocator searchPathLocator() {
    return new NativeEnvironmentRepository(environment);
  }

  @Bean
  @Primary
  public EnvironmentRepository environmentRepository() {
    return new CustomEnvironmentRepository();
  }

}
