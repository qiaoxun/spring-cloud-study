package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class ItemServiceApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(ItemServiceApp.class);
    }
}