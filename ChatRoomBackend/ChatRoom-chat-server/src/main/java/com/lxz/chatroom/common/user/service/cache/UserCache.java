package com.lxz.chatroom.common.user.service.cache;

import com.lxz.chatroom.common.user.dao.BlackDao;
import com.lxz.chatroom.common.user.dao.UserRoleDao;
import com.lxz.chatroom.common.user.domain.entity.Black;
import com.lxz.chatroom.common.user.domain.entity.UserRole;
import com.lxz.chatroom.common.user.domain.enums.BlackTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/6
 */
@Component
public class UserCache {
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private BlackDao blackDao;

    @Cacheable(cacheNames = "user", key = "'roles:' + #uid")
    public Set<Long> getRolesSet(Long uid) {
        List<UserRole> userRoles = userRoleDao.getByUid(uid);
        return userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
    }

    @Cacheable(cacheNames = "user", key = "'blackList'") // IMPORTANT: key is a SpringEl expression, inner string must be surrounded by ''
    public Map<Integer, Set<String>> getBlackMap() {
        Map<Integer, List<Black>> collect = blackDao.list().stream().collect(Collectors.groupingBy(Black::getType));
        Map<Integer, Set<String>> result = new HashMap<>();
        collect.forEach((type, list) -> {
            result.put(type, list.stream().map(Black::getTarget).collect(Collectors.toSet()));
        });
        return result;
    }

    @CacheEvict(cacheNames = "user", key = "'blackList'")
    public Map<Integer, Set<String>> evictBlackMap() {
        return null;
    }
}
