package com.vodafone.deliveryapi;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Deployment(resources ={("classpath*:/processes/**/*.bpmn"),("classpath*:/decisions/**/*.dmn")})
//@EnableScheduling
public class DeliveryapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryapiApplication.class, args);
    }

}
