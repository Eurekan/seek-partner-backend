package com.yupi.yupao.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 标签
 * </p>
 *
 * @author Eureka
 * @since 2024-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tag")
public class Tag implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 父标签 id
     */
    private Long parentId;

    /**
     * 0 - 不是, 1 - 父标签
     */
    private Integer isParent;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;
}
