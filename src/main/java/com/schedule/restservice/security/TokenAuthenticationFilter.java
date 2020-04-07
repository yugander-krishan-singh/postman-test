package com.schedule.restservice.security;

import com.schedule.restservice.Exception.RequestBodyException;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger LOGGER = Logger.getLogger(TokenAuthenticationFilter.class.getName());

    private static final String BEARER = "Bearer";
    private static final String ACCESS_TOKEN = "access_token";

    TokenAuthenticationFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
                                                final HttpServletResponse response) {

        LOGGER.log(Level.INFO, "Inside attemptAuthentication method of class TokenAuthenticationFilter.java");

        //System.out.println("Inside attemptAuthentication method of class TokenAuthenticationFilter.java \n");
        //Optional tokenParam = Optional.ofNullable(request.getHeader(ACCESS_TOKEN));
        String token = request.getHeader(ACCESS_TOKEN);
        //token= StringUtils.removeStart(token, "Bearer").trim();

        Authentication requestAuthentication = new UsernamePasswordAuthenticationToken(token, token);

        Authentication authentication = null;

        try {
            authentication = getAuthenticationManager().authenticate(requestAuthentication);
        }catch (Exception e) {
            LOGGER.log(Level.INFO,"Exception caught");
            throw new RequestBodyException.UserException("User not found. Incorrect access token");
        }

        return authentication;
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult) throws IOException, ServletException {

        LOGGER.log(Level.INFO, "Inside successfulAuthentication method of class TokenAuthenticationFilter.java");

        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}
