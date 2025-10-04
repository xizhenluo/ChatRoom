package com.lxz.chatroom.common.user.dao;

import com.lxz.chatroom.common.user.domain.entity.ItemConfig;
import com.lxz.chatroom.common.user.mapper.ItemConfigMapper;
import com.lxz.chatroom.common.user.service.IItemConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * item function config 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/xizhenluo">xizhenluo</a>
 * @since 2025-10-02
 */
@Service
public class ItemConfigDao extends ServiceImpl<ItemConfigMapper, ItemConfig> {

    public List<ItemConfig> getByType(Integer itemType) {
        return lambdaQuery().eq(ItemConfig::getType, itemType)
                .list();
    }
}
