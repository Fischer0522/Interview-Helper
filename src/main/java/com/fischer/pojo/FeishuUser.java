package com.fischer.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("feishu_user")
public class FeishuUser {
    @ExcelProperty(value = "姓名")
    private String username;
    @ExcelProperty(value = "年级")
    private String grade;
    @ExcelProperty(value = "牛客昵称")
    private String newCoder;
    @ExcelProperty(value = "QQ")
    private String qq;
    @ExcelProperty(value = "手机号")
    private String phone;


}
