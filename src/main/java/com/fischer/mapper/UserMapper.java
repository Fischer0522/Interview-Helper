package com.fischer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fischer.pojo.NowcoderUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author fisher
 */
@Mapper
public interface UserMapper extends BaseMapper<NowcoderUser> {

    @Update("update code_user c set c.verify = 2 where c.verify = 1 and c.username = #{username}")
    int updateEmailStatus(@Param("username") String username);

}
