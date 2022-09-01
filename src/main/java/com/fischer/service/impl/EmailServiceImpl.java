package com.fischer.service.impl;

import com.fischer.pojo.FeishuUser;
import com.fischer.pojo.UserWithScore;
import com.fischer.service.EmailService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author fisher
 */
@Service
@NoArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private JavaMailSenderImpl javaMailSender;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.receive}")
    private String receive;
    @Value("${mail.receivePort}")
    private String receivePort;

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
    public void  sendInterview(FeishuUser user){

        String email = user.getQq()+EMAIL_END;
        Context context = new Context();
        // 设置项目变量
        context.setVariable("project", "InterviewEmail");
        // 设置作者变量
        context.setVariable("author", "FlyingStudio");

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

    @Override
    public void sendExam(FeishuUser user) {

        String email = user.getQq()+EMAIL_END;
        Context context = new Context();
        // 设置项目变量
        context.setVariable("project", "ExamEmail");
        // 设置作者变量
        context.setVariable("author", "FlyingStudio");

        // 模板引擎指向welcome.html页面
        String emailTemplate = templateEngine.process("bishi", context);


        MimeMessage message = javaMailSender.createMimeMessage();
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setHost(host);

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
            helper.setFrom(username);
            helper.setTo(email);
            helper.setSubject("翔工作室笔试邀请");
            helper.setText(emailTemplate, true);
            javaMailSender.send(message);
            log.info("html格式笔试邮件发送成功,当前的用户为"+user);
        } catch (Exception e) {
            log.info("html格式笔试邮件发送失败,当前的用户为:"+user);
            e.printStackTrace();
        }

    }

    @Override
    public void receiveMail() {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol","imap");
        props.setProperty("mail.imap.host",receive);
        props.setProperty("mail.imap.port",receivePort);

        Session session = Session.getInstance(props);

        try {
            Store store = session.getStore("imap");

                store.connect(username,password);

            Folder folder = store.getFolder("INBOX");

            folder.open(Folder.READ_WRITE);

            Message[] messages = folder.getMessages();

            log.info("邮件总数："+messages.length);



        } catch ( Exception e) {
            e.printStackTrace();
        }

    }
}



