package com.example.codedeploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CodedeployApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodedeployApplication.class, args);
    }

}
