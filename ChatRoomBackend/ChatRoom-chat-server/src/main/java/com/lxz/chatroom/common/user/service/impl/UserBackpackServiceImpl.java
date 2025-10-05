package com.lxz.chatroom.common.user.service.impl;

import com.lxz.chatroom.common.common.domain.enums.YesOrNo;
import com.lxz.chatroom.common.common.service.LockService;
import com.lxz.chatroom.common.common.utils.AssertUtil;
import com.lxz.chatroom.common.user.dao.UserBackpackDao;
import com.lxz.chatroom.common.user.domain.entity.UserBackpack;
import com.lxz.chatroom.common.user.domain.enums.IdempotentEnum;
import com.lxz.chatroom.common.user.service.UserBackpackService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/5
 */
@Service
public class UserBackpackServiceImpl implements UserBackpackService {

    @Autowired
    private LockService lockService;
    @Autowired
    private UserBackpackDao userBackpackDao;

    @Override
    public void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        // create an idempotent key
        String idempotent = getIdempotent(uid, idempotentEnum, businessId);
        //
        lockService.executeWithLock("acquireItem: " + idempotent, () -> {
            // tell the idempotent, check whether has been allocated
            UserBackpack userBackpack = userBackpackDao.getByIdemPotent(idempotent);
            if (Objects.nonNull(userBackpack)) {
                return; // if idempotent already exists, corresponding item has been successfully allocated, then return
            }
            // if not exist, then allocate
            UserBackpack insertBackpack = UserBackpack.builder().uid(uid)
                    .itemId(itemId)
                    .status(YesOrNo.NO.getStatus())
                    .idempotent(idempotent)
                    .build();
            userBackpackDao.save(insertBackpack);
        });
    }

    private String getIdempotent(Long uid, IdempotentEnum idempotentEnum, String businessId) {
        return String.format("%d_%d_%s", uid, idempotentEnum.getType(), businessId);
    }
}
