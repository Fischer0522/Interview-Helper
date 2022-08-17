package com.fischer.service.impl;

import com.fischer.pojo.UserWithScore;
import com.fischer.service.EmailService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

/**
 * @author fisher
 */
@Service
@NoArgsConstructor
public class EmailServiceImpl implements EmailService {

    private JavaMailSenderImpl javaMailSender;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.password}")
    private String password;

    private final String EMAIL_END = "@qq.com";

    @Resource
    TemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(
                            JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;

    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void send(UserWithScore user) {

        String email = user.getQq()+"@qq.com";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setHost(host);
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("面试通知");
        simpleMailMessage.setText(user.getUsername()+"同学，恭喜你通过笔试，现邀请你参加面试");
        javaMailSender.send(simpleMailMessage);

    }


    @Override
    public void  sendHtmlFile(UserWithScore user){

        String email = user.getQq()+EMAIL_END;
        Context context = new Context();
        // 设置项目变量
        context.setVariable("project", "InterviewEmail");
        // 设置作者变量
        context.setVariable("author", "fischer");

        // 模板引擎指向welcome.html页面
        String emailTemplate = templateEngine.process("interview", context);


        MimeMessage message = javaMailSender.createMimeMessage();
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setHost(host);

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
            helper.setFrom(username);
            helper.setTo(email);
            helper.setSubject("翔工作室面试邀请");
            helper.setText(emailTemplate, true);
            javaMailSender.send(message);
            System.out.println("html格式邮件发送成功");
        } catch (Exception e) {
            System.out.println("html格式邮件发送失败");
            e.printStackTrace();
        }


    }


        }



