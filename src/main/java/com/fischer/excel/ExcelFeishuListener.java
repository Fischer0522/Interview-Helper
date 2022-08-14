package com.fischer.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fischer.exception.BizException;
import com.fischer.mapper.FeishuMapper;
import com.fischer.pojo.FeishuUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ExcelFeishuListener implements ReadListener<FeishuUser> {

    private FeishuMapper feishuMapper;
    public ExcelFeishuListener(FeishuMapper feishuMapper) {
        this.feishuMapper = feishuMapper;
    }

    private List<FeishuUser> users = new ArrayList<>();
    @Override
    public void invoke(FeishuUser feishuUser, AnalysisContext analysisContext) {
        users.add(feishuUser);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println(users);
    saveData();
    }

    private void saveData() {
        for(FeishuUser f : users) {
            LambdaQueryWrapper<FeishuUser> lqw = new LambdaQueryWrapper<>();
            lqw.eq(FeishuUser::getUsername,f.getUsername());
            FeishuUser feishuUser = feishuMapper.selectOne(lqw);
            if(Objects.isNull(feishuUser)) {
                feishuMapper.insert(f);
            } else {
                throw new BizException(500,"数据库中已存在该条数据");
            }

        }


    }
}
