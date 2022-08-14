package com.fischer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fischer.pojo.FeishuUser;
import com.fischer.excel.UserWithScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeishuMapper extends BaseMapper<FeishuUser> {
    @Select("select f.*,c.rank,c.score from feishu_user f,code_user c where c.username = f.new_coder order by c.score desc limit  #{limit}")
    List<UserWithScore> getInfo(@Param("limit") Integer limit);

}
