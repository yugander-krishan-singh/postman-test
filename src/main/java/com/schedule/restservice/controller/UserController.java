package com.schedule.restservice.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.schedule.restservice.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.schedule.restservice.bean.*;

import javax.websocket.server.PathParam;


@RestController
public class UserController {

    @Autowired
    private UserAuthService userAuthService;

    @GetMapping(value = "/api/users/user/{id}",produces = "application/json")
    public User getUserDetail(@PathVariable Long id){
        //return userAuthService.findById(id);
        return new User("testUser");
    }

    @PostMapping("/user/register")
    public String registerUser(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password) {
        //return new Greeting(counter.incrementAndGet(), String.format(template, name));
        return userAuthService.registerNewUser(userName, password);
    }

    @GetMapping("/user")
    public String getUserToken(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password) {

        return userAuthService.getUserToken(userName, password);
    }

}
