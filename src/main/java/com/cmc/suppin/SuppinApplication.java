package com.cmc.suppin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SuppinApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuppinApplication.class, args);
    }

}
