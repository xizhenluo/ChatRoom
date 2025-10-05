package com.lxz.chatroom.common.user.service;

import com.lxz.chatroom.common.user.domain.enums.IdempotentEnum;

/**
 * <p>
 * user backpack 服务类
 * </p>
 *
 * @author <a href="https://github.com/xizhenluo">xizhenluo</a>
 * @since 2025-10-02
 */
public interface UserBackpackService {
    /**
     * allocate an item to a user
     * @param uid             user's id
     * @param itemId          item's id
     * @param idempotentEnum  idempotent type
     * @param businessId      idempotent unique identifier
     */
    void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId);
}
