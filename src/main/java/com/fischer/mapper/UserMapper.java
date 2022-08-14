package com.fischer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fischer.pojo.CodeUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author fisher
 */
@Mapper
public interface UserMapper extends BaseMapper<CodeUser> {

}
