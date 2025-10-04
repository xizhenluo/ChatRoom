package com.lxz.chatroom.common.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * item function config
 * </p>
 *
 * @author <a href="https://github.com/xizhenluo">xizhenluo</a>
 * @since 2025-10-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("item_config")
public class ItemConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId("id")
    private Long id;

    /**
     * item type 1 modifyNameCard 2badge
     */
    @TableField("type")
    private Integer type;

    /**
     * item image
     */
    @TableField("img")
    private String img;

    /**
     * function description
     */
    @TableField("description")
    private String description;

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
