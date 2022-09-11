package com.example.researchgate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages={"com.example.researchgate.Dao"})
public class ResearchgateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResearchgateApplication.class, args);
    }

}
