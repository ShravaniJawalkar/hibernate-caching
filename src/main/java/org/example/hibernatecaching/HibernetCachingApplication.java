package org.example.hibernatecaching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HibernetCachingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernetCachingApplication.class, args);
    }

}
