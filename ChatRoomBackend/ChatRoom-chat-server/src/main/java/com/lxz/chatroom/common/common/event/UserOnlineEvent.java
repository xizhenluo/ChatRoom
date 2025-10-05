package com.lxz.chatroom.common.common.event;

import com.lxz.chatroom.common.user.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/5
 */
@Getter
public class UserOnlineEvent extends ApplicationEvent {
    private User user;

    public UserOnlineEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
