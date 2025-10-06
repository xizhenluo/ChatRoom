package com.lxz.chatroom.common.user.dao;

import com.lxz.chatroom.common.user.domain.entity.Black;
import com.lxz.chatroom.common.user.domain.enums.BlackTypeEnum;
import com.lxz.chatroom.common.user.mapper.BlackMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * black list 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/xizhenluo">xizhenluo</a>
 * @since 2025-10-06
 */
@Service
public class BlackDao extends ServiceImpl<BlackMapper, Black> {

}
