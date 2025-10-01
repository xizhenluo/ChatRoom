package com.lxz.chatroom.common.common.thread;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadFactory;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/1
 */
@AllArgsConstructor
public class MyThreadFactory implements ThreadFactory {
    private static final MyUncaughtExceptionHandler MY_UNCAUGHT_EXCEPTION_HANDLER = new MyUncaughtExceptionHandler();
    private ThreadFactory primary;

    @Override
    public Thread newThread(Runnable r) {
        Thread t = primary.newThread(r);
        t.setUncaughtExceptionHandler(MY_UNCAUGHT_EXCEPTION_HANDLER);
        return t;
    }
}
