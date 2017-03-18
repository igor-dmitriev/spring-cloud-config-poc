package com.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Import(Config.class)
public class Client {
  @Autowired
  Data container;

  public static void main(String[] args) {
    SpringApplication.run(Client.class, args);
  }

  @RequestMapping("/value")
  public String value() {
    return container.getValue();
  }

}
