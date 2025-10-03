package com.lxz.chatroom.common.user.dao;

import com.lxz.chatroom.common.common.domain.enums.YesOrNo;
import com.lxz.chatroom.common.user.domain.entity.UserBackpack;
import com.lxz.chatroom.common.user.mapper.UserBackpackMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * user backpack 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/xizhenluo">xizhenluo</a>
 * @since 2025-10-02
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack> {
    /**
     * @return
     * @Description sql count the amount of certain item indexed itemId hand owned bu user uid
     */
    public Integer getCountByValidItemId(Long uid, Long itemId) {
        return lambdaQuery().eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemId)
                .eq(UserBackpack::getStatus, YesOrNo.NO.getStatus())
                .count();
    }

    public UserBackpack getFirstValidItem(Long uid, Long itemId) {
        return lambdaQuery().eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemId)
                .eq(UserBackpack::getStatus, YesOrNo.NO.getStatus())
                .orderByAsc(UserBackpack::getItemId)
                .last("limit 1")
                .one();
    }

    public boolean useItem(UserBackpack modifyNameCard) {
        return lambdaUpdate().eq(UserBackpack::getId, modifyNameCard.getId())
                .eq(UserBackpack::getStatus, YesOrNo.NO.getStatus())
                .set(UserBackpack::getStatus, YesOrNo.YES.getStatus())
                .update();
    }
}
