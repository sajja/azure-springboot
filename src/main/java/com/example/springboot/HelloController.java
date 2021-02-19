package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot MASTER!";
    }

    @RequestMapping("/vault")
    public String vault() {
        return "Greetings from Spring Boot VAULT!";
    }

    @RequestMapping("/mysql")
    public String mysql() throws Exception {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://172.17.0.1:3306/sajithDB", "sajith1", "sajith")) {

            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (Exception e) {
            throw e;
        }

        return "Greetings from Spring Boot MYSQL!";
    }
}
