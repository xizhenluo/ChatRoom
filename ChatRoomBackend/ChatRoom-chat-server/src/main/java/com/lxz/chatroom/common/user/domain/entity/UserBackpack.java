package com.lxz.chatroom.common.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * user backpack
 * </p>
 *
 * @author <a href="https://github.com/xizhenluo">xizhenluo</a>
 * @since 2025-10-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_backpack")
public class UserBackpack implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * uid
     */
    @TableField("uid")
    private Long uid;

    /**
     * item id
     */
    @TableField("item_id")
    private Long itemId;

    /**
     * use status 0.to be used 1.used
     */
    @TableField("status")
    private Integer status;

    /**
     * idempotent sign
     */
    @TableField("idempotent")
    private String idempotent;

    /**
     * createtime
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * updatetime
     */
    @TableField("update_time")
    private Date updateTime;


}
