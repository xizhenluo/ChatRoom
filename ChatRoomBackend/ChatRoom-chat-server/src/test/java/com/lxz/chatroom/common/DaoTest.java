package com.lxz.chatroom.common;

import com.lxz.chatroom.common.common.utils.JwtUtils;
import com.lxz.chatroom.common.user.domain.enums.IdempotentEnum;
import com.lxz.chatroom.common.user.domain.enums.ItemEnum;
import com.lxz.chatroom.common.user.service.LoginService;
import com.lxz.chatroom.common.user.service.UserBackpackService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description
 * @author LuoXizhen
 * @date 2025-09-22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DaoTest {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private JwtUtils jwtUtils;
//    @Autowired
//    private RedissonClient redissonClient;
    @Autowired
    private LoginService loginService;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private UserBackpackService userBackpackService;

    @Test
    public void jwtTest() {
        System.out.println(jwtUtils.createToken(10028L));
    }

    @Test
    public void redisTest() {
        String testToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjExMDA3LCJjcmVhdGVUaW1lIjoxNzU5MjU3MDg4fQ.1MZgOBeYay8GrGc01AR3T6yywuNdO_hzU525Mbd132o";
        Long validUid = loginService.getValidUid(testToken);
        System.out.println(validUid);
    }

//    @Test
//    public void redissonTest() {
//        RLock lock = redissonClient.getLock("123");
//        lock.lock();
//        System.out.println();
//        lock.unlock();
//    }

    @Test
    public void threadTest() throws InterruptedException {
        threadPoolTaskExecutor.execute(() -> {
            if (1 == 1) {
                log.error("123");
                throw new RuntimeException("1243");
            }
        });
        Thread.sleep(200);
    }

    @Test
    public void test() throws WxErrorException {
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(1, 1000);
        String url = wxMpQrCodeTicket.getUrl();
        System.out.println(url);
    }

    @Test
    public void acquireItemTest() {
        userBackpackService.acquireItem(11007L, ItemEnum.REG_TOP10_BADGE.getId(), IdempotentEnum.UID, 11007L + "top10 badge");
    }
}
