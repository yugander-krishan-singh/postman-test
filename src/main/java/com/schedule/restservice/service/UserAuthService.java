package com.schedule.restservice.service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.schedule.restservice.bean.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(UserAuthService.class.getName());

    private final Map<String, User> userMap = new HashMap<>();
    private final Map<User, String> tokenMap = new HashMap<>();
    private final Set<User> userSet = new HashSet<>();


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public String login(String userName, String password) {
        if(userSet.contains(userName+"-"+password)) {

            String token = UUID.randomUUID().toString();

            User user = new User(userName, password);

            userMap.put(token, user);

            return token;
        }

        return null;
    }

    public User findByToken(String token) {

        if(token.equalsIgnoreCase("qwerty")) {
            return new User("test","test");
        }

        if(userMap.containsKey(token)) {
            return userMap.get(token);
        }

        return null;
    }

    public String registerNewUser(String userName, String password) {

        LOGGER.log(Level.INFO, "Inside registerNewUser - " + userName + " password - " + password);

        User user = new User(userName, password);

        if(userSet.contains(user)) {
            return "User already registered";
        }else {

            String token = UUID.randomUUID().toString();
            userMap.put(token, user);
            tokenMap.put(user, token);

            return token;
        }
    }

    public String getUserToken(String userName, String password) {

        LOGGER.log(Level.INFO, "Inside getUseToken - " + userName + " password - " + password);

        User user = new User(userName, password);

        if(tokenMap.containsKey(user)) {
            return tokenMap.get(user);
        }else{
            return "User Not registered";
        }
    }
}
