package com.lxz.chatroom.common.common.event.listener;

import com.lxz.chatroom.common.common.event.UserRegisterEvent;
import com.lxz.chatroom.common.user.dao.UserDao;
import com.lxz.chatroom.common.user.domain.entity.User;
import com.lxz.chatroom.common.user.domain.enums.IdempotentEnum;
import com.lxz.chatroom.common.user.domain.enums.ItemEnum;
import com.lxz.chatroom.common.user.service.UserBackpackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/5
 */
@Component
public class UserRegisterListener {
    @Autowired
    private UserBackpackService userBackpackService;
    @Autowired
    private UserDao userDao;

    @Async
    @TransactionalEventListener(classes = UserRegisterEvent.class, phase = TransactionPhase.AFTER_COMMIT) // after the transaction
    public void sendModifyNameCard(UserRegisterEvent event) { // parameter is the event being listened
        User user = event.getUser();
        userBackpackService.acquireItem(user.getId(), ItemEnum.MODIFY_NAME_CARD.getId(), IdempotentEnum.UID, user.getId().toString());
    }

    @Async
    @TransactionalEventListener(classes = UserRegisterEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sendBadge(UserRegisterEvent event) {
        User user = event.getUser();
        int registeredCount = userDao.count();
        if (registeredCount <= 10) {
            userBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP10_BADGE.getId(), IdempotentEnum.UID, user.getId().toString());
        }
        if (registeredCount <= 100) {
            userBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP100_BADGE.getId(), IdempotentEnum.UID, user.getId().toString());
        }
    }
}
