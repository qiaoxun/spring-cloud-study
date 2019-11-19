package com.study;

import com.study.zipkin.configuration.TracingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * Hello world!
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrix
@EnableFeignClients
@Import(TracingConfiguration.class)
public class OrderServiceApp {
    public static void main( String[] args ) {
        SpringApplication.run(OrderServiceApp.class);
    }
}
