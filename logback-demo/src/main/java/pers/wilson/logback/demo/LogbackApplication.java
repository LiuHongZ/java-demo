package pers.wilson.logback.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackApplication {

    private static final Logger logger = LoggerFactory.getLogger(LogbackApplication.class);

    public static void main(String[] args) {
        logger.info("info~~");
        logger.warn("warn~~");
        logger.error("error~~");
    }
}
