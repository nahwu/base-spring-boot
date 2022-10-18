package com.nahwu.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.nahwu.base"})
@EnableJpaRepositories(basePackages = {"com.nahwu.base.repository"})
public class BaseApplication {
    private static final Logger logger = LoggerFactory.getLogger(BaseApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
        logger.info("___ Main started up ___");
    }
}
