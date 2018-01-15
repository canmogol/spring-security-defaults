package com.fererlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class RestMongoExampleApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RestMongoExampleApplication.class, args);
    }

}
