package com.lxz.chatroom.common.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/5
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true) // ignore those fields not listed here
public class IpDetail implements Serializable {
    private String ip;
    private String isp;
    private String ispId;

    private String country;
    private String countryId;

    private String region;
    private String regionId;

    private String city;
    private String cityId;

    private String county;
    private String countyId;
}
