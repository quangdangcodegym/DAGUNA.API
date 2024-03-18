package com.cg.spblaguna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpbLagunaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpbLagunaApplication.class, args);
    }

}
