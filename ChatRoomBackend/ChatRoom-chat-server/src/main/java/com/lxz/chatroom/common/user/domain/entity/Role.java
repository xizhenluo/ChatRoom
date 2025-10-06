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
 * role table
 * </p>
 *
 * @author <a href="https://github.com/xizhenluo">xizhenluo</a>
 * @since 2025-10-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * role name
     */
    @TableField("name")
    private String name;

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
