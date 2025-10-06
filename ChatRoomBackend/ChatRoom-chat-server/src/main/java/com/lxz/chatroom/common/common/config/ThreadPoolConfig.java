package com.lxz.chatroom.common.common.config;

import com.lxz.chatroom.common.common.thread.MyThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/1
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {
    /**
     * general thread pool for the project
     */
    public static final String CHATROOM_EXECUTOR = "chatroomExecutor";
    /**
     * thread pool for websocket commu
     */
    public static final String WS_EXECUTOR = "websocketExecutor";

    @Override
    public Executor getAsyncExecutor() {
        return chatroomExecutor();
    }

    /**
     * how to call the main thread pool in other methods:
     * // @Autowired
     * // @Qualifier(ThreadPoolConfig.CHATROOM_EXECUTOR)
     * // private ThreadPoolTaskExecutor threadPoolTaskExecutor;
     * @return
     */
    @Bean(CHATROOM_EXECUTOR) // need bean-name when autowire a ThreadPoolTaskExecutor with @Qualifier to specify which bean should be used
    @Primary
    public ThreadPoolTaskExecutor chatroomExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setWaitForTasksToCompleteOnShutdown(true); // elegant shutdown
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("chatroom-executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());// let the caller-thread to do if full
        executor.setThreadFactory(new MyThreadFactory(executor)); // use Decorator Pattern here to expand functions based on given class
        executor.initialize();
        return executor;
    }

    @Bean(WS_EXECUTOR)
    public ThreadPoolTaskExecutor websocketExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setWaitForTasksToCompleteOnShutdown(true); // elegant shutdown
        executor.setCorePoolSize(16); // should be larger because mainly I/O
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("websocket-executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());// discard if full
        executor.setThreadFactory(new MyThreadFactory(executor)); // use Decorator Pattern here to expand functions based on given class
        executor.initialize();
        return executor;
    }
}
