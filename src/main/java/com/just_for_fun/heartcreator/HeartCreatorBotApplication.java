package com.just_for_fun.heartcreator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HeartCreatorBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeartCreatorBotApplication.class, args);
    }

}
