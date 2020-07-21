package pers.wilson.log4j2.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4j2Application {

    private static final Logger logger = LoggerFactory.getLogger(Log4j2Application.class);

    public static void main(String[] args) {
        logger.info("log4j2 ---------- info");
        logger.warn("log4j2 ---------- warn");
        logger.error("log4j2 ---------- error");
        logger.debug("log4j2 ---------- debug");
    }
}
