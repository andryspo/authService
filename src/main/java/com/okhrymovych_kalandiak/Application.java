package com.okhrymovych_kalandiak;

import com.okhrymovych_kalandiak.entity.User;
import com.okhrymovych_kalandiak.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {

    @Autowired
    private PasswordEncoder encoder;

    private Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public static final String USERNAME = "alex";

    public static final String PASSWORD = "alex";

    @Bean
    public CommandLineRunner demo(UserRepo repository) {
        return (args) -> {
            try {
                repository.save(new User("alex", encoder.encode("alex")));
                logger.info("created user");
            } catch (DataIntegrityViolationException e) {
                logger.error("user already in db");
            }
        };
    }
}
