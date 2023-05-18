package com.fufu.yygh.user.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.fufu.yygh.user.mapper")
public class UserConfig {
}
