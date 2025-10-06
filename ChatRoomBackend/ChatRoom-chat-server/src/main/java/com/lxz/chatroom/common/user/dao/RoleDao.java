package com.lxz.chatroom.common.user.dao;

import com.lxz.chatroom.common.user.domain.entity.Role;
import com.lxz.chatroom.common.user.mapper.RoleMapper;
import com.lxz.chatroom.common.user.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * role table 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/xizhenluo">xizhenluo</a>
 * @since 2025-10-06
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role> {

}
