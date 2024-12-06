package com.yupi.yupao.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用删除队伍请求体
 */
@Data
public class DeleteRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 队伍id
     */
    private Long id;
}
