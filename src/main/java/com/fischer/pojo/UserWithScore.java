package com.fischer.pojo;

import com.fischer.pojo.FeishuUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fisher
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserWithScore extends FeishuUser {

    private Integer rank;

    private Double score;

    private Integer verify;


}
