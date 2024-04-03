package com.cg.spblaguna.util;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailUtil {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("reservationangsana@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // Set HTML content to true
            mailSender.send(message);
            System.out.println("Gửi email thành công ...");
        } catch (MessagingException e) {
            System.out.println("Lỗi gửi email: " + e.getMessage());
        }
    }

    public void sendEmailWithAttachment(String toEmail, String subject, String body, byte[] fileBytes, String fileName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("reservationangsana@gmail.com");
        helper.setTo(toEmail);
        helper.setText(body);
        helper.setSubject(subject);

        DataSource dataSource = new ByteArrayDataSource(fileBytes, "application/msword");
        helper.addAttachment(fileName, dataSource);

        mailSender.send(message);
        System.out.println("Gửi email trả kết quả thành công ...");
    }


}
