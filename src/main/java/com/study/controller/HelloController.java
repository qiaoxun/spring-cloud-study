package com.study.controller;

import com.study.domain.HelloObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloController {

    private AtomicLong counter = new AtomicLong();

    @GetMapping("/hello")
    public HelloObject getHelloObject() {
        HelloObject helloObject = new HelloObject();
        helloObject.setMessage("Hi there! you are number " + counter.incrementAndGet());
        return helloObject;
    }

}
