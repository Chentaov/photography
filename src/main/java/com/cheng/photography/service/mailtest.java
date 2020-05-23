package com.cheng.photography.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class mailtest {
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;


    public void sendMail(String to, String title, String content, int id) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
//        message.setText(content+"http://localhost:8081/actEmail?id="+id);
        message.setText(content+"http://106.14.45.97:8081/actEmail?id="+id);
        mailSender.send(message);
        System.out.println("邮件发送成功");
    }

    public void sendMail(String to, String title, String content,String email) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
//        message.setText(content+"http://localhost:8081/resetPasswordByEmail?email="+email);
        message.setText(content+"http://106.14.45.97:8081/resetPasswordByEmail?email="+email);
        mailSender.send(message);
        System.out.println("邮件发送成功");
    }
}