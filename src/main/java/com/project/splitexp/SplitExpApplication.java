package com.project.splitexp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SplitExpApplication {

  public static void main(String[] args) {
    SpringApplicationBuilder springApplicationBuilder = null;
    springApplicationBuilder = new SpringApplicationBuilder(SplitExpApplication.class);
    springApplicationBuilder.run(args);
  }
}
