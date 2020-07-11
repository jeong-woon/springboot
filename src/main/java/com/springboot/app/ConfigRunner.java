package com.springboot.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ConfigRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(ConfigRunner.class);

    @Autowired
    private String hello;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.debug("============================");
        logger.debug("bean : " + hello);
        logger.debug("============================");
    }
}
