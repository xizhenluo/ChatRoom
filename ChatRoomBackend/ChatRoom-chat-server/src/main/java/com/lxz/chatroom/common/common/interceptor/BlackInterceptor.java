package com.lxz.chatroom.common.common.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import com.lxz.chatroom.common.common.domain.dto.RequestInfo;
import com.lxz.chatroom.common.common.exception.HttpErrorEnum;
import com.lxz.chatroom.common.common.utils.RequestHolder;
import com.lxz.chatroom.common.user.domain.enums.BlackTypeEnum;
import com.lxz.chatroom.common.user.service.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/6
 */
@Component
public class BlackInterceptor implements HandlerInterceptor {
    @Autowired
    private UserCache userCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<Integer, Set<String>> blackMap = userCache.getBlackMap();
        RequestInfo requestInfo = RequestHolder.get();
        // check banned uids
        if (isInBlackList(requestInfo.getUid(), blackMap.get(BlackTypeEnum.UID.getType()))) {
            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
            return false;
        }
        // check banned ips
        if (isInBlackList(requestInfo.getIp(), blackMap.get(BlackTypeEnum.IP.getType()))) {
            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
            return false;
        }
        return true;
    }

    private boolean isInBlackList(Object target, Set<String> blackTargets) {
        if (Objects.isNull(target) || CollectionUtil.isEmpty(blackTargets)) return false;
        return blackTargets.contains(target.toString());
    }
}
