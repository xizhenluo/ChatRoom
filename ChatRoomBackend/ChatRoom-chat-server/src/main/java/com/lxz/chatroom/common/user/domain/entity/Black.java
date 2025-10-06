package com.lxz.chatroom.common.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * black list
 * </p>
 *
 * @author <a href="https://github.com/xizhenluo">xizhenluo</a>
 * @since 2025-10-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("black")
public class Black implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * black target type 1.ip 2uid
     * @see com.lxz.chatroom.common.user.domain.enums.BlackTypeEnum
     */
    @TableField("type")
    private Integer type;

    /**
     * black target
     */
    @TableField("target")
    private String target;

    /**
     * create time
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * update time
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
