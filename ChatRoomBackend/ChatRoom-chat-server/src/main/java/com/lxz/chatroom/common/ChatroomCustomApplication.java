package com.lxz.chatroom.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LuoXizhen
 * @date  2025-09-21
 */
@SpringBootApplication(scanBasePackages = "com.lxz.chatroom")
public class ChatroomCustomApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatroomCustomApplication.class);
    }
}
