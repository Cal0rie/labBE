package com.youfan.bussiness;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.TimeZone;

/**
 * 启动程序
 *
 * @author youfan
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan({"com.youfan.*" })
public class YoufanBussinessApplication
{
    public static void main(String[] args)
    {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SpringApplication.run(YoufanBussinessApplication.class, args);
        System.out.println("程序启动成功");
    }
}
