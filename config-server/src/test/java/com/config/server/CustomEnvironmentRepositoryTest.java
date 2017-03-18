package com.config.server;

import org.junit.After;
import org.junit.Test;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.junit.Assert.assertEquals;


public class CustomEnvironmentRepositoryTest {
  private ConfigurableApplicationContext context;

  @After
  public void close() {
    if (this.context != null) {
      this.context.close();
    }
  }

  @Test
  public void test() {
    context = new SpringApplicationBuilder(TestConfiguration.class).web(false).run();
    EnvironmentRepository repository = this.context.getBean(EnvironmentRepository.class);
    Environment environment = repository.findOne("does not matter", "does not matter", null);
    PropertySource propertySource = environment.getPropertySources().get(0);
    assertEquals(1, environment.getPropertySources().size());
    assertEquals("first", environment.getPropertySources().get(0).getSource().get("testKey"));
  }

  @Configuration
  @EnableConfigServer
  @Import({
      CustomEnvironmentRepositoryConfiguration.class,
      PropertyPlaceholderAutoConfiguration.class,
  })
  protected static class TestConfiguration {

  }
}
