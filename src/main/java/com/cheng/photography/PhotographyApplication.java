package com.cheng.photography;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhotographyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotographyApplication.class, args);
    }

}
