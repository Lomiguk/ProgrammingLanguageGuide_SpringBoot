package com.vsu.skibin.coursework.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.vsu")
@ComponentScan(basePackages = {"com.vsu"})
public class ProgLangGuideApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProgLangGuideApplication.class, args);
    }
}
