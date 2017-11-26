package com.okhrymovych_kalandiak.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService service;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.service)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(1)
    public static class ApiAuth extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/api/login").permitAll()
                    .antMatchers("/api/version").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/version").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/planningWork").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                    .anyRequest().authenticated()
                    .and()

                    // We filter the api/login requests
                    .addFilterBefore(new JWTLoginFilter("/api/login", authenticationManager()),
                            UsernamePasswordAuthenticationFilter.class)
                    // And filter other requests to check the presence of JWT in header
                    .addFilterBefore(new JWTAuthenticationFilter(),
                            UsernamePasswordAuthenticationFilter.class);
        }
    }

}