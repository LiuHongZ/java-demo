package pers.wilson.lombok.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LombokApplication {

    private static final Logger logger = LoggerFactory.getLogger(LombokApplication.class);

    public static void main(String[] args) {
        logger.info("info~~");
        logger.warn("warn~~");
        logger.error("error~~");
    }
}
