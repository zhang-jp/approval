package com.test.approval.flow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.test.approval.flow.repository.dao")
@EnableAsync
public class App
{
    public static void main(String[] args)
    {
        // 程序启动入口
        SpringApplication.run(App.class, args);
    }
}
