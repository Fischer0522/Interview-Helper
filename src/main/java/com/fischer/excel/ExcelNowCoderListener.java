package com.fischer.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fischer.exception.BizException;
import com.fischer.mapper.UserMapper;
import com.fischer.pojo.NowcoderUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ExcelNowCoderListener implements ReadListener<NowcoderUser> {

    @Autowired
    private UserMapper userMapper;

    public ExcelNowCoderListener(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    private List<NowcoderUser> nowcoderUserList = new ArrayList<>();
    @Override
    public void invoke(NowcoderUser nowcoderUser, AnalysisContext analysisContext) {
        nowcoderUserList.add(nowcoderUser);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println(nowcoderUserList);
        saveData();

    }

    private void saveData() {
        for (NowcoderUser user: nowcoderUserList

             ) {
            LambdaQueryWrapper<NowcoderUser> lqw = new LambdaQueryWrapper<>();
            lqw.eq(NowcoderUser::getUsername,user.getUsername());
            NowcoderUser nowcoderUser = userMapper.selectOne(lqw);
            if(Objects.isNull(nowcoderUser)) {
                userMapper.insert(user);
            } else {
                throw new BizException(500,"数据库中已存在该数据");
            }


        }


    }
}
