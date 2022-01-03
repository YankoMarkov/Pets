package com.yanmark.pets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PetsApplication {

  public static void main(String[] args) {
    SpringApplication.run(PetsApplication.class, args);
    System.out.println("Open http://localhost:8000/");
  }
}
