package com.fischer.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "code_user")
public class CodeUser {

    private Integer rank;

    private String team;

    private String username;

    private String school;

    private Double score;

    private String runtime;

    private String a;

    private String similar;

}
