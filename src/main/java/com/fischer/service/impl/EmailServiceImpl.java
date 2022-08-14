package com.fischer.service.impl;

import com.fischer.excel.UserWithScore;
import com.fischer.service.EmailService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.time.Duration;

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
    public void sendHtml(String email) {

        MimeMessage message = javaMailSender.createMimeMessage();
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setHost(host);

        String content = "\n" +
                "<table style=\"width: 99.8%;height:99.8% \">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div style=\"border-radius: 10px 10px 10px 10px;font-size:16px;color: #555555;width: 666px;font-family:'Century Gothic','Trebuchet MS','Hiragino Sans GB',微软雅黑,'Microsoft Yahei',Tahoma,Helvetica,Arial,'SimSun',sans-serif;margin:50px auto;border:1px solid #eee;max-width:100%;background: #ffffff repeating-linear-gradient(-45deg,#fff,#fff 1.125rem,transparent 1.125rem,transparent 2.25rem);box-shadow: 0 1px 5px rgba(0, 0, 0, 0.15);\">\n" +
                "                        <div style=\"width:100%;background:#4af6de;color:#ffffff;border-radius: 10px 10px 0 0;background-image: -moz-linear-gradient(0deg, rgb(67, 198, 184), rgb(255, 209, 244));background-image: -webkit-linear-gradient(0deg, #4af6de, #6bf2ad);height: 66px;\">\n" +
                "                            <p style=\"font-size:24px;word-break:break-all;padding: 18px 32px;margin:0;background-color: hsla(0,0%,100%,.4);border-radius: 10px 10px 0 0; text-align: center;\">翔工作室2022面试邀请</p>\n" +
                "                        </div>\n" +
                "                        <div style=\"margin: 0px auto; text-align: center;\">\n" +
                "                            <img src=\"http://atcumt.com/static/ico/logo.png\" width=\"249\" height=\"50\" vspace=\"20\">\n" +
                "                        </div>\n" +
                "                        <div style=\"margin:0px auto;width:80%\">\n" +
                "                            <p style=\"font-size:18px; margin-top:0px\">\n" +
                "                                同学你好，翔工作室诚邀你参加技术组面试。<br><br>【面试时间】：2022年10月16日晚上7点—9点<br><br>【面试地点】：易班发展中心大厅（图书馆正对面桃四楼大厅)<br><br>面试只是彼此了解的一个过程，请不要有太大压力。<br><br>收到请回复邮件：“姓名+同意面试”。如果你的面试时间不合适，请回复邮件：“姓名+你合适的面试时间”<br><br>翔工作室技术组祝你面试顺利！\n" +
                "                            </p>\n" +
                "                            <table style=\"margin-bottom: 2em; border-collapse: separate; border-spacing: 20px; table-layout: fixed; width: 100%; padding: 0; vertical-align: top; text-align: left;\">\n" +
                "                                <tbody style=\"display: table-row-group; vertical-align: middle; border-color: inherit;\">\n" +
                "                                    <tr style=\"padding: 0; vertical-align: top; text-align: left;\">\n" +
                "                                        <td>\n" +
                "                                            <a href=\"mailto:flyingstudio@vip.qq.com\" style=\"display: inline-block; max-width: 80%; padding: 0.75em 2.5em 0.75em 2.5em; background: #ea9448; border-radius: 6px; text-align: center; color: #FFF; font-size: 16px; text-transform: uppercase;\">回复邮件</a>\n" +
                "                                        </td>\n" +
                "                                        <td style=\"vertical-align: middle; text-align: right; text-decoration: none;\">\n" +
                "                                            <a style=\"color: #3498db;\" href=\"https://qm.qq.com/cgi-bin/qm/qr?k=y2Rj8EmvYbmuY8kZBgYe_Kre7Sr1Zn65&noverify=0\">Flying Studio</a>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n";
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
            helper.setFrom(username);
            helper.setTo(email);
            helper.setSubject("面试通知");
            helper.setText(content, true);
            javaMailSender.send(message);
            System.out.println("html格式邮件发送成功");
        } catch (Exception e) {
            System.out.println("html格式邮件发送失败");
            e.printStackTrace();
        }
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



