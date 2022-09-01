package com.fischer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fischer.controller.ExcelController;
import com.fischer.mapper.FeishuMapper;
import com.fischer.pojo.FeishuUser;
import com.fischer.pojo.UserWithScore;
import com.fischer.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class NewCoderExcelApplicationTests {
    @Autowired
    private EmailService emailService;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private FeishuMapper feishuMapper;

    @Test
    void contextLoads() {
        List<String> users = new ArrayList<>();
        users.add("1809327837");
        users.add("1730214056");
        users.add("845630236");
        users.add("2417170480");
        for (String qq : users) {
            UserWithScore user = new UserWithScore();
            user.setQq(qq);
            emailService.sendExam(user);
        }


    }

    @Test
    void receiveEmailTest(){

        emailService.receiveMail();
    }


    @Test
    void readExcel(){
        excelController.readExcel();
    }
    @Test
    void getByExam(){

        for (FeishuUser feishuUser : feishuMapper.getByExam()) {
            System.out.println(feishuUser);
        }

    }

    @Test

    void TestSend() {


        FeishuUser feishuUser = new FeishuUser();
        LambdaQueryWrapper<FeishuUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(FeishuUser::getUsername,"于芯邑");
        for (FeishuUser user : feishuMapper.getByExam()) {

            try {
                Thread.sleep(3000);
                emailService.sendExam(user);
                System.out.println(user);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //emailService.sendExam(user);
        }
        //feishuUser.setQq("1730214056");

    }

}
