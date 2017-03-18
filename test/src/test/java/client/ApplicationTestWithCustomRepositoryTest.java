package client;


import com.client.Client;
import com.config.server.ConfigServer;
import com.util.PropertyUtil;
import com.util.RepositoryPath;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Client.class)
@WebIntegrationTest(randomPort = true)
public class ApplicationTestWithCustomRepositoryTest {

  public static final String KEY = "testKey";
  private static final String VALUE = "first";
  private static int configPort = 8888;
  private static ConfigurableApplicationContext server;
  private static String backup;
  private int port = 8080;

  @BeforeClass
  public static void startConfigServer() throws IOException {
    backup = PropertyUtil.loadProperties(new File(RepositoryPath.HARDCODED_PATH)).getProperty(KEY);
    updateProperty(KEY, VALUE);
    startConfServer();
  }

  @AfterClass
  public static void revertProperty() throws IOException {
    updateProperty(KEY, backup);
  }

  private static void updateProperty(String key, String value) throws IOException {
    PropertyUtil.updateProperty(RepositoryPath.HARDCODED_PATH, key, value);
  }

  private static void startConfServer() throws IOException {
    server = SpringApplication.run(ConfigServer.class,
        "--server.port=" + configPort,
        "--spring.config.name=server",
        "--spring.profiles.active=custom");
    configPort = ((EmbeddedWebApplicationContext) server)
        .getEmbeddedServletContainer().getPort();
    System.setProperty("config.port", "" + configPort);
  }

  @Test
  public void testFlow() throws IOException, InterruptedException {
    String testValue = getValue();
    System.out.println(testValue);
    assertEquals(VALUE, testValue);

    PropertyUtil.updateProperty(RepositoryPath.HARDCODED_PATH, KEY, "second");

    refreshBeans(); // refresh all beans with @RefreshScope manually

    testValue = getValue();

    assertEquals("second", testValue);
    System.out.println(testValue);
  }

  private String getValue() {
    return new TestRestTemplate().getForObject("http://localhost:" + port + "/value", String.class);
  }

  private void refreshBeans() {
    new TestRestTemplate().postForObject("http://localhost:" + port + "/refresh", "", Object.class);
  }

}
