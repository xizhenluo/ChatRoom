package com.lxz.chatroom.common.common.event.listener;

import com.lxz.chatroom.common.common.event.UserOnlineEvent;
import com.lxz.chatroom.common.user.dao.UserDao;
import com.lxz.chatroom.common.user.domain.entity.User;
import com.lxz.chatroom.common.user.domain.enums.UserActiveEnum;
import com.lxz.chatroom.common.user.service.IpService;
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
public class UserOnlineListener {
    @Autowired
    UserDao userDao;
    @Autowired
    IpService ipService;

    @Async
    @TransactionalEventListener(classes = UserOnlineEvent.class, phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true) // no transaction so TransactionalEvent won't be excuted, so need to set fallbackExecution true
    public void updateUserDB(UserOnlineEvent onlineEvent) {
        // update user's info in db
        User user = onlineEvent.getUser();
        User update = new User();
        update.setId(user.getId());
        update.setLastOptTime(user.getLastOptTime());
        update.setIpInfo(user.getIpInfo());
        update.setActiveStatus(UserActiveEnum.ONLINE.getStatus());
        userDao.updateById(update);

        // parse ip
        ipService.refreshIpDetailAsync(user.getId());
    }

}
