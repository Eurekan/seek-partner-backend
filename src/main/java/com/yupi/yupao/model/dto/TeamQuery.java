package com.yupi.yupao.model.dto;


import com.yupi.yupao.common.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class TeamQuery extends PageRequest {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "id列表")
    private List<Long> idList;

    @ApiModelProperty(value = "搜索关键词")
    private String searchText;

    @ApiModelProperty(value = "队伍名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "最大人数")
    private Integer maxNum;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "0 - 公开，1 - 私有，2 - 加密")
    private Integer status;
}
