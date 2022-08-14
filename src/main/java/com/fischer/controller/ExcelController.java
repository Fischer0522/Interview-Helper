package com.fischer.controller;

import com.alibaba.excel.EasyExcel;
import com.fischer.excel.*;
import com.fischer.exception.BizException;
import com.fischer.mapper.FeishuMapper;
import com.fischer.mapper.UserMapper;
import com.fischer.pojo.CodeUser;
import com.fischer.pojo.FeishuUser;
import com.fischer.pojo.ResponseResult;
import com.fischer.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ExcelController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FeishuMapper feishuMapper;

    @Autowired
    private EmailService emailService;

    @GetMapping("excel")
    @Transactional(rollbackFor = {Exception.class})
    public ResponseEntity readExcel(){
        String filename = "D:\\learn_software\\new.xls";

         EasyExcel.read(filename, CodeUser.class,new ExcelListener(userMapper)).sheet().doRead();

        String filename1 = "D:\\learn_software\\test.xlsx";
         EasyExcel.read(filename1, FeishuUser.class,new ExcelFeishuListener(feishuMapper)).sheet().doRead();

        return ResponseEntity.ok().body("");

    }
    @GetMapping("email")
    public ResponseEntity<ResponseResult> sendEmail(@RequestParam(value = "limit",defaultValue = "5") Integer limit){

        List<UserWithScore> infos = feishuMapper.getInfo(limit);
        for(UserWithScore user : infos) {

            try {
                emailService.sendHtmlFile(user);
            } catch (Exception e) {
                log.error("邮箱发送失败，当前QQ为："+user.getQq());
                throw new BizException(500,"邮件发送失败！当前qq为："+user.getQq());

            }
        }
        return ResponseEntity.ok(new ResponseResult(200,"success",infos));
    }

    @GetMapping("info")

    public ResponseEntity<ResponseResult> listUsers(@RequestParam(value = "limit",defaultValue = "2000") Integer limit) {

        return ResponseEntity.ok(new ResponseResult(200,"success",feishuMapper.getInfo(limit)));


    }


}
