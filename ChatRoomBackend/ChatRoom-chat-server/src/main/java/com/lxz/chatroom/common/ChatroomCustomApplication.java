package com.lxz.chatroom.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LuoXizhen
 * @date  2025-09-21
 */
@SpringBootApplication(scanBasePackages = "com.lxz.chatroom")
@MapperScan({"com.lxz.chatroom.common.**.mapper"})
public class ChatroomCustomApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatroomCustomApplication.class);
    }
}
