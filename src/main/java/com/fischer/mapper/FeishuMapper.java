package com.fischer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fischer.pojo.FeishuUser;
import com.fischer.pojo.UserWithScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FeishuMapper extends BaseMapper<FeishuUser> {
    @Select("select f.*,c.rank,c.score,c.verify from feishu_user f,code_user c where c.username = f.new_coder order by c.score desc limit  #{limit}")
    List<UserWithScore> getInfo(@Param("limit") Integer limit);

    @Select("select f.*,c.rank,c.score,c.verify from feishu_user f ,code_user c where c.verify = 1 and c.username = f.new_coder order by c.score desc ")
    List<UserWithScore> getAllByVerify();

    @Select("select * from feishu_user f  where f.exam is null or f.exam = '是'")
    List<FeishuUser> getByExam();





}
