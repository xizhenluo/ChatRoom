package com.lxz.chatroom.common.common.aspect;

import com.lxz.chatroom.common.common.annotation.RedissonLock;
import com.lxz.chatroom.common.common.service.LockService;
import com.lxz.chatroom.common.common.utils.SpringElUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/5
 */
@Component
@Aspect
@Order(0) // IMPORTANT: ensure the @RedissonLock is earlier executed before @Transactional, transaction is inside of lock, lock->transaction->finish-transaction->unlock
public class RedissonLockAspect {
    @Autowired
    LockService lockService;

    @Around("@annotation(redissonLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedissonLock redissonLock) throws Throwable {
        // obtain the prefixKey
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String prefix = StringUtils.isBlank(redissonLock.prefixKey()) ? SpringElUtils.getMethodKey(method) : redissonLock.prefixKey();
        // obtain the key
        String key = SpringElUtils.parseSpringEl(method, joinPoint.getArgs(), redissonLock.key());
        return lockService.executeWithLock(prefix + ":" + key, redissonLock.waitTime(), redissonLock.timeUnit(), joinPoint::proceed);
    }
}
