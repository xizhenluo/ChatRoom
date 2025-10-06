package com.lxz.chatroom.common.user.dao;

import com.lxz.chatroom.common.user.domain.entity.UserRole;
import com.lxz.chatroom.common.user.mapper.UserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * user-role mapping 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/xizhenluo">xizhenluo</a>
 * @since 2025-10-06
 */
@Service
public class UserRoleDao extends ServiceImpl<UserRoleMapper, UserRole> {

    public List<UserRole> getByUid(Long uid) {
        return lambdaQuery().eq(UserRole::getUid, uid)
                .list();
    }
}
