package com.schedule.restservice.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schedule.restservice.bean.*;


@RestController
public class UserController {

    @GetMapping("/user")
    public User user(@RequestParam(value = "userId", defaultValue = "testId") String userId, @RequestParam(value = "userName", defaultValue = "testName") String userName) {
        //return new Greeting(counter.incrementAndGet(), String.format(template, name));
        return new User(userId, userName);
    }
}