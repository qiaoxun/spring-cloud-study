package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 *
 */
@EnableZuulProxy
@SpringBootApplication
public class ApiGateWayApp {
    public static void main( String[] args ) {
        SpringApplication.run(ApiGateWayApp.class);
    }
}
