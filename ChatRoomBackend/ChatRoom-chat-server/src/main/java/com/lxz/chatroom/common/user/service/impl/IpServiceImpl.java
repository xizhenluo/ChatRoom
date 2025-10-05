package com.lxz.chatroom.common.user.service.impl;

import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lxz.chatroom.common.common.domain.vo.resp.ApiResult;
import com.lxz.chatroom.common.common.utils.JsonUtils;
import com.lxz.chatroom.common.user.dao.UserDao;
import com.lxz.chatroom.common.user.domain.entity.IpDetail;
import com.lxz.chatroom.common.user.domain.entity.IpInfo;
import com.lxz.chatroom.common.user.domain.entity.User;
import com.lxz.chatroom.common.user.service.IpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/5
 */
@Slf4j
@Service
public class IpServiceImpl implements IpService, DisposableBean {
    private static ExecutorService executor = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(500), new NamedThreadFactory("refresh-ipDetail", false));

    @Autowired
    UserDao userDao;

    @Override
    public void refreshIpDetailAsync(Long uid) {
        executor.execute(() -> {
            User user = userDao.getById(uid);
            IpInfo ipInfo = user.getIpInfo();
            if (Objects.isNull(ipInfo)) return;

            String newIp = ipInfo.needRefresh();
            if (StringUtils.isBlank(newIp)) return;
            IpDetail ipDetail = tryGetIpDetailOrNullThreeTimes(newIp);
            if (Objects.nonNull(ipDetail)) {
                ipInfo.refreshIpDetail(ipDetail);
                User update = new User();
                update.setId(uid);
                update.setIpInfo(ipInfo);
                userDao.updateById(update);
            }
        });
    }

    private static IpDetail tryGetIpDetailOrNullThreeTimes(String newIp) {
        for (int i = 0; i < 3; i++) {
            IpDetail ipDetail = getIpDetailOrNull(newIp);
            if (Objects.nonNull(ipDetail)) return ipDetail;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error("tryGetIpDetailOrNullThreeTimes InterruptedException", e);
            }
        }
        return null;
    }

    private static IpDetail getIpDetailOrNull(String newIp) {
        try {
            String url = String.format("https://ip.taobao.com/outGetIpInfo?ip=%s&accessKey=alibaba-inc", newIp);
            String data = HttpUtil.get(url);
            ApiResult<IpDetail> ipDetailApiResult = JsonUtils.toObj(data, new TypeReference<ApiResult<IpDetail>>() {});
            return ipDetailApiResult.getData();
        } catch (Exception e) { // exceptions should be thrown and return null, or it won't retry
            return null;
        }
    }

    // throughput capacity test
    public static void main(String[] args) {
        Date beginTime = new Date();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executor.execute(() -> {
                IpDetail ipDetail = tryGetIpDetailOrNullThreeTimes("117.85.133.4");
                if (Objects.nonNull(ipDetail)) {
                    Date endTime = new Date();
                    System.out.println(String.format("succeed at %d time, takes time %d milliseconds", finalI, endTime.getTime() - beginTime.getTime()));
                }
            });
        }

    }

    // elegant shutdown
    @Override
    public void destroy() throws Exception {
        executor.shutdown();
        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) { // wait for maximal 30 seconds
            if (log.isErrorEnabled()) {
                log.error("Time out while waiting for executor [{}] to terminate", executor);
            }
        }
    }
}
