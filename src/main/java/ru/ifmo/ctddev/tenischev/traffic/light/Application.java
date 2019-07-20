package ru.ifmo.ctddev.tenischev.traffic.light;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Simple application to host traffic-light.<br/>
 * Provides also latest news and status of nightly test.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
