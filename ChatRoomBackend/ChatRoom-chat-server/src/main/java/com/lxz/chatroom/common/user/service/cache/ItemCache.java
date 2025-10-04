package com.lxz.chatroom.common.user.service.cache;

import com.lxz.chatroom.common.user.dao.ItemConfigDao;
import com.lxz.chatroom.common.user.domain.entity.ItemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/4
 */
@Component
public class ItemCache {
    @Autowired
    ItemConfigDao itemConfigDao;

    @Cacheable(cacheNames = "item", key = "'itemsByType: ' + #itemType") // visit cache, if failed then call the method
    public List<ItemConfig> getByType(Integer itemType) {
        return itemConfigDao.getByType(itemType);
    }

    // @CachePut  actively refresh cache

    @CacheEvict(cacheNames = "item", key = "'itemsByType: ' + #itemType") // clear cache
    public void evictByType(Integer itemType) {

    }
}
