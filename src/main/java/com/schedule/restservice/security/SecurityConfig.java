package com.schedule.restservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.schedule.restservice.security.TokenAuthenticationProvider;

import java.util.logging.Level;
import java.util.logging.Logger;


@EnableWebSecurity
public class SecurityConfig  {

    private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class.getName());

    @Configuration
    public static class TokenSecurityConfig extends WebSecurityConfigurerAdapter {
        private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
                //new AntPathRequestMatcher("/api/**")
                new AntPathRequestMatcher("/slot")
        );

        TokenAuthenticationProvider provider;

        public TokenSecurityConfig(final TokenAuthenticationProvider authenticationProvider) {
            super();
            this.provider = authenticationProvider;
        }


        /*
        Does the authentication
         */
        @Override
        protected void configure(final AuthenticationManagerBuilder auth) {
            //auth.
            LOGGER.log(Level.INFO, "inside configure with AuthenticationManagerBuilder parameter");
            auth.authenticationProvider(provider);
        }

        @Override
        public void configure(final WebSecurity webSecurity) {

            LOGGER.log(Level.INFO, "inside configure with WebSecurity parameter");
            webSecurity.ignoring().antMatchers("/token/**");
        }



        /*
        Does the authorization
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {

            LOGGER.log(Level.INFO, "inside configure with HttpSecurity parameter");
        /*

        http
                .authorizeRequests()
                .antMatchers("/", "/greeting", "/register").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().csrf().disable()
                .logout().permitAll();
                //.permitAll();

        */

            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .exceptionHandling()
                    .and()
                    .authenticationProvider(provider)
                    .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
                    .authorizeRequests()
                    //.requestMatchers(PROTECTED_URLS)
                    .antMatchers("/","/api/**","/slot")
                    .authenticated()
                    .and()
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .logout().disable();
        }

        @Bean
        TokenAuthenticationFilter authenticationFilter() throws Exception {

            LOGGER.log(Level.INFO, "inside authenticationFilter func.");

            final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(PROTECTED_URLS);
            filter.setAuthenticationManager(authenticationManager());
            //filter.setAuthenticationSuccessHandler(successHandler());
            return filter;
        }

        @Bean
        AuthenticationEntryPoint forbiddenEntryPoint() {

            LOGGER.log(Level.INFO, "inside AuthenticationEntryPoint func.");
            return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
        }
    }


    /*
    @Configuration
    @Order(1)
    public static class BasicAuthSecurityConfig extends WebSecurityConfigurerAdapter {


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/register")
                    .authenticated()
                    .and().httpBasic()
                    .and().csrf().disable()
                    .logout().permitAll();


        }

    }
    */

}