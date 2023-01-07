package ru.annikonenkov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 1
@SpringBootApplication
// @EnableTransactionManagement - в SpringBoot уже активировано
public class TryStringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TryStringbootApplication.class, args);
    }

}
