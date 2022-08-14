package com.fischer.excel;

import com.fischer.pojo.FeishuUser;
import lombok.Data;

@Data
public class UserWithScore extends FeishuUser {

    private Integer rank;

    private Double score;


}
