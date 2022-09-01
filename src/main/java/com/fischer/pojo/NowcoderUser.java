package com.fischer.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "code_user")
public class NowcoderUser {

    private Integer rank;

    private String team;

    private String username;

    private String school;

    private Double score;

    private String runtime;

    private String a;

    private String similar;
    // 0为未通过笔试 1为通过笔试  2为通过笔试且已经发送面试邀请
    private Integer verify;

}
