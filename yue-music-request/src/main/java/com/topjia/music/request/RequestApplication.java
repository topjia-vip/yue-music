package com.topjia.music.request;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestApplication.class, args);
    }

}
