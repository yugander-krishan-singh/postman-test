package com.schedule.restservice.UserService;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/user")
    public User testUser(@RequestParam(value = "userId") String userId, @RequestParam(value = "userName") String userName) {
        return new User(userId, userName);
    }
}
