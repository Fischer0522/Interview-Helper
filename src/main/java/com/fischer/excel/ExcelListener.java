package com.fischer.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fischer.exception.BizException;
import com.fischer.mapper.UserMapper;
import com.fischer.pojo.CodeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ExcelListener implements ReadListener<CodeUser> {

    @Autowired
    private UserMapper userMapper;

    public ExcelListener(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    private List<CodeUser> codeUserList = new ArrayList<>();
    @Override
    public void invoke(CodeUser codeUser, AnalysisContext analysisContext) {
        codeUserList.add(codeUser);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println(codeUserList);
        saveData();

    }

    private void saveData() {
        for (CodeUser user: codeUserList

             ) {
            LambdaQueryWrapper<CodeUser> lqw = new LambdaQueryWrapper<>();
            lqw.eq(CodeUser::getUsername,user.getUsername());
            CodeUser codeUser = userMapper.selectOne(lqw);
            if(Objects.isNull(codeUser)) {
                userMapper.insert(user);
            } else {
                throw new BizException(500,"数据库中已存在该数据");
            }


        }


    }
}
