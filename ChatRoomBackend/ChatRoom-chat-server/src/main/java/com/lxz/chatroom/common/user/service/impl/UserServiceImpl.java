package com.lxz.chatroom.common.user.service.impl;

import com.lxz.chatroom.common.common.annotation.RedissonLock;
import com.lxz.chatroom.common.common.event.UserBlackEvent;
import com.lxz.chatroom.common.common.event.UserRegisterEvent;
import com.lxz.chatroom.common.common.utils.AssertUtil;
import com.lxz.chatroom.common.user.dao.BlackDao;
import com.lxz.chatroom.common.user.dao.ItemConfigDao;
import com.lxz.chatroom.common.user.dao.UserBackpackDao;
import com.lxz.chatroom.common.user.dao.UserDao;
import com.lxz.chatroom.common.user.domain.entity.*;
import com.lxz.chatroom.common.user.domain.enums.BlackTypeEnum;
import com.lxz.chatroom.common.user.domain.enums.ItemEnum;
import com.lxz.chatroom.common.user.domain.enums.ItemTypeEnum;
import com.lxz.chatroom.common.user.domain.vo.req.BlackReq;
import com.lxz.chatroom.common.user.domain.vo.resp.BadgeResp;
import com.lxz.chatroom.common.user.domain.vo.resp.UserInfoResp;
import com.lxz.chatroom.common.user.service.UserService;
import com.lxz.chatroom.common.user.service.adapter.UserAdapter;
import com.lxz.chatroom.common.user.service.cache.ItemCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/9/29
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserBackpackDao userBackpackDao;
    @Autowired
    private ItemCache itemCache;
    @Autowired
    private ItemConfigDao itemConfigDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private BlackDao blackDao;

    @Override
    @Transactional // ensure that the saving and events belong to one transaction
    public Long register(User insertUser) {
        userDao.save(insertUser);
        // register events: allocate name card
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, insertUser));
        return insertUser.getId();
    }

    @Override
    public UserInfoResp getUserInfo(Long uid) {
        User user = userDao.getById(uid);
        Integer modifyNameChance = userBackpackDao.getCountByValidItemId(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        return UserAdapter.buildUserInfo(user, modifyNameChance);
    }

    @Override
    @RedissonLock(key = "#uid")
    @Transactional(rollbackFor = Exception.class) // for any type exception occurred in any step in this method, rollback
    public void modifyName(Long uid, String name) {
        // check 1: name can't have been existing
        User oldUser = userDao.getByName(name);
        AssertUtil.isEmpty(oldUser, "name has been existing"); // require oldUser must be null
//        if (Objects.nonNull(oldUser)) {
//            throw new BusinessException("name has been existing");
//        }
        // check 2: have rest chances
        UserBackpack modifyNameCard = userBackpackDao.getFirstValidItem(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        AssertUtil.isNotEmpty(modifyNameCard, "you have run out your chances to modify name");
        // then use the modifyNameCard (keep transactional)
        boolean successfulUse = userBackpackDao.useItem(modifyNameCard);
        if (successfulUse) {
            // if used modifyNameCard successfully, then change the name
            userDao.modifyName(uid, name);
            // todo delete cache
        }
    }

    @Override
    public List<BadgeResp> getBadges(Long uid) {
        // enquire all badges
        List<ItemConfig> badges = itemCache.getByType(ItemTypeEnum.BADGE.getType());
        // enquire all items hold by uid
        List<UserBackpack> backpacks = userBackpackDao.getByItemIds(uid, badges.stream().map(ItemConfig::getId).collect(Collectors.toList()));
        // enquire the badge being equipped currently by uid
        User user = userDao.getById(uid);

        return UserAdapter.buildBadgeResp(badges, backpacks, user);
    }

    @Override
    public void equipBadge(Long uid, Long itemId) {
        // ensure target itemId is owned
        UserBackpack item = userBackpackDao.getFirstValidItem(uid, itemId);
        AssertUtil.isNotEmpty(item, "you haven't owned this badge yet");
        // ensure this owned item is badge
        ItemConfig itemConfig = itemConfigDao.getById(item.getItemId());
        AssertUtil.equal(itemConfig.getType(), ItemTypeEnum.BADGE.getType(), "only badges can be equipped");
        // equip badge
        userDao.equipBadge(uid, itemId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void black(BlackReq blackReq) {
        Long uid = blackReq.getUid();
        Black black = new Black();
        black.setType(BlackTypeEnum.UID.getType());
        black.setTarget(uid.toString());
        blackDao.save(black);
        // black his ips too
        User targetUser = userDao.getById(uid);
        blackIp(Optional.ofNullable(targetUser.getIpInfo()).map(IpInfo::getCreateIp).orElse(null));
        blackIp(Optional.ofNullable(targetUser.getIpInfo()).map(IpInfo::getCreateIp).orElse(null));
        // push an inform to frontend
        applicationEventPublisher.publishEvent(new UserBlackEvent(this, targetUser));
    }

    private void blackIp(String ip) {
        if (StringUtils.isBlank(ip)) return;
        try { // if createIp.equals(updateIp), will throws DuplicateException
            Black black = new Black();
            black.setType(BlackTypeEnum.IP.getType());
            black.setTarget(ip);
            blackDao.save(black);
        } catch (Exception e) {

        }
    }


}
