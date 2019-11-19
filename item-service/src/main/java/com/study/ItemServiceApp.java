package com.study;

import com.study.zipkin.configuration.TracingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

/**
 * Hello world!
 *
 */
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
@Import(TracingConfiguration.class)
public class ItemServiceApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(ItemServiceApp.class);
    }
}