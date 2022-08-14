package com.fischer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
public class NewCoderExcelApplication {


    @Bean
    public JavaMailSenderImpl javaMailSender(){
        JavaMailSenderImpl jms=new JavaMailSenderImpl();
        return jms;
    }
    public static void main(String[] args) {
        SpringApplication.run(NewCoderExcelApplication.class, args);
    }

}
