package com.schedule.restservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.schedule.restservice.service.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider  {

    private static final Logger LOGGER = Logger.getLogger(TokenAuthenticationProvider.class.getName());

    @Autowired
    UserAuthService authService;

    @Override
    protected void additionalAuthenticationChecks(final UserDetails d, final UsernamePasswordAuthenticationToken auth) {
        // Nothing to do
    }

    @Override
    protected UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken authentication) {

        LOGGER.log(Level.INFO, "Inside retrieveUser func. of TokenAuthenticationProvider class");

        final Object token = authentication.getCredentials();
        return authService.findByToken(String.valueOf(token)) ;

        //return null;
    }
}
