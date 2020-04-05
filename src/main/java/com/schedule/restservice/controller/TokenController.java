package com.schedule.restservice.controller;

import com.schedule.restservice.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/token")
    public String getToken(@RequestParam("username") final String username, @RequestParam("password") final String password){
        String token= userAuthService.login(username,password);
        if(token == null || token.isEmpty()){
            return "no token found";
        }
        return token;
    }
}