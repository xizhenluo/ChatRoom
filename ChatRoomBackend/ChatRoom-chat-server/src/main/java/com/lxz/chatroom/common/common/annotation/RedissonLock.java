package com.lxz.chatroom.common.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description annotation of distributed lock
 * @date 2025/10/5
 */
@Retention(RetentionPolicy.RUNTIME) // effect when runtime
@Target(ElementType.METHOD) // effect on methods
public @interface RedissonLock {

    /**
     * the prefix of key, default value: fully-qualified-name of method
     */
    String prefixKey() default "";

    /**
     * the key which support SpringEl expressions
     */
    String key();

    /**
     * time waiting for lock, default value: -1 (no wait, refuse if fail)
     */
    int waitTime() default -1;

    /**
     * time unit, default value: millisecond
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
