package com.lxz.chatroom.common.user.service.impl;

import com.lxz.chatroom.common.user.domain.enums.RoleEnum;
import com.lxz.chatroom.common.user.service.RoleService;
import com.lxz.chatroom.common.user.service.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/6
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private UserCache userCache;

    @Override
    public boolean hasRower(Long uid, RoleEnum roleEnum) {
        Set<Long> rolesSet = userCache.getRolesSet(uid);
        return isAdmin(rolesSet) || rolesSet.contains(roleEnum.getId());
    }

    private boolean isAdmin(Set<Long> rolesSet) {
        return rolesSet.contains(RoleEnum.ADMIN.getId());
    }
}
