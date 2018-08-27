package com.epam.task.rout.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//import org.springframework.cloud.client.loadbalancer.LoadBalanced;

//@RibbonClient(name = "city-rout-handler", configuration = RibbonConfiguration.class)
@EnableEurekaClient
@SpringBootApplication
public class RoutRestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoutRestServiceApplication.class, args);
    }

    @LoadBalanced
    @Bean
    @Qualifier(value = "lbRestTemplate")
    public RestTemplate loadBalancedRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    @Qualifier(value = "justRestTemplate")
    public RestTemplate justRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
