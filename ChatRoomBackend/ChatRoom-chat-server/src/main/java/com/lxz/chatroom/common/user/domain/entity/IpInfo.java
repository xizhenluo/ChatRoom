package com.lxz.chatroom.common.user.domain.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/5
 */
@Data
public class IpInfo implements Serializable {
    private String createIp;
    private IpDetail createIpDetail;
    private String updateIp;
    private IpDetail updateIpDetail;

    public void refreshIp(String ip) {
        if (StringUtils.isBlank(ip)) return;
        if (StringUtils.isBlank(createIp)) {
            createIp = ip;
        }
        updateIp = ip;
    }

    public String needRefresh() {
        boolean notNeedRefresh = Optional.ofNullable(updateIpDetail)
                .map(IpDetail::getIp)
                .filter(ip -> Objects.equals(ip, updateIp))
                .isPresent();
        return notNeedRefresh ? null : updateIp;
    }

    public void refreshIpDetail(IpDetail ipDetail) {
        if (Objects.equals(createIp, ipDetail.getIp())) {
            createIpDetail = ipDetail;
        }
        if (Objects.equals(updateIp, ipDetail.getIp())) {
            updateIpDetail = ipDetail;
        }
    }
}
