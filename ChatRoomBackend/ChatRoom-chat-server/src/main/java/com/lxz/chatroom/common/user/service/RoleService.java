package com.lxz.chatroom.common.user.service;

import com.lxz.chatroom.common.user.domain.enums.RoleEnum;

/**
 * <p>
 * role table 服务类
 * </p>
 *
 * @author <a href="https://github.com/xizhenluo">xizhenluo</a>
 * @since 2025-10-06
 */
public interface RoleService {
    /**
     * whether user uid has certain power
     */
    boolean hasRower(Long uid, RoleEnum roleEnum);
}
