package com.fischer.controller;

import com.alibaba.excel.EasyExcel;
import com.fischer.excel.*;
import com.fischer.exception.BizException;
import com.fischer.mapper.FeishuMapper;
import com.fischer.mapper.UserMapper;
import com.fischer.pojo.NowcoderUser;
import com.fischer.pojo.FeishuUser;
import com.fischer.pojo.ResponseResult;
import com.fischer.pojo.UserWithScore;
import com.fischer.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ExcelController {
    private UserMapper userMapper;

    private FeishuMapper feishuMapper;

    private EmailService emailService;

    @Value("${filepath.feishu}")
    private String feishuPath;
    @Value(("${filepath.nowcoder}"))
    private String nowcoderPath;

    @Autowired
    public ExcelController (UserMapper userMapper,
                            FeishuMapper feishuMapper,
                            EmailService emailService){
        this.emailService = emailService;
        this.feishuMapper = feishuMapper;
        this.userMapper = userMapper;
    }

    @GetMapping("excel")
    @Transactional(rollbackFor = {Exception.class})
    public ResponseEntity readExcel(){


         // EasyExcel.read(feishuPath, NowcoderUser.class,new ExcelNowCoderListener()).sheet().doRead();

         EasyExcel.read(feishuPath, FeishuUser.class,new ExcelFeishuListener(feishuMapper)).sheet().doRead();

        return ResponseEntity.ok().body("");

    }



    @Transactional(rollbackFor = {Exception.class})
    @GetMapping("email")
    public ResponseEntity<ResponseResult> sendEmail(){

        List<UserWithScore> infos = feishuMapper.getAllByVerify();
        if (infos.size() == 0) {
            return ResponseEntity.ok(new ResponseResult(200,"暂无更多需要发送",infos));
        }
        for(UserWithScore user : infos) {
            String username = user.getNewCoder();

            try {
                emailService.sendInterview(user);
                userMapper.updateEmailStatus(username);
            } catch (Exception e) {
                log.error("邮箱发送失败，当前QQ为："+user.getQq());
                throw new BizException(500,"邮件发送失败！当前qq为："+user.getQq());

            }
        }
        return ResponseEntity.ok(new ResponseResult(200,"success",infos));
    }

    @GetMapping("info")

    public ResponseEntity<ResponseResult> listUsers() {

        return ResponseEntity.ok(new ResponseResult(200,"success",feishuMapper.getAllByVerify()));


    }




}
