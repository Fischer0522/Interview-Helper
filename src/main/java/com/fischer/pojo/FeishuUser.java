package com.fischer.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("feishu_user")
public class FeishuUser {
    @ExcelProperty(value = "姓名")
    private String username;
    @ExcelProperty(value = "年级")
    private String grade;
    @ExcelProperty(value = "牛客网昵称（务必准确）")
    private String newCoder;
    @ExcelProperty(value = "QQ（务必准确）")
    private String qq;
    @ExcelProperty(value = "手机号")
    private String phone;
    @ExcelProperty(value = "是否参加笔试")
    private String exam;


}
