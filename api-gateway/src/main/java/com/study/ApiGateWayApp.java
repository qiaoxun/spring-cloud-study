package com.study;

import com.study.zipkin.configuration.TracingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Import;

/**
 *
 */
@EnableZuulProxy
@SpringBootApplication
@Import(TracingConfiguration.class)
public class ApiGateWayApp {
    public static void main( String[] args ) {
        SpringApplication.run(ApiGateWayApp.class);
    }
}
