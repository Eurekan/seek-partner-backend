package com.yupi.yupao.easyExcel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 星球用户信息表格
 */
@Data
public class XingQiuTableUserInfo {

    // 星球编号
    @ExcelProperty("成员编号")
    private String planetCode;

    // 用户名
    @ExcelProperty("成员昵称")
    private String username;

}