package com.cg.spblaguna.util;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;

import java.util.Date;
import java.util.Properties;

public class Email {
    //Email: reservationangsana@gmail.com
    //PassWord: szhrayxkgzrqymne

    static final String from = "reservationangsana@gmail.com";
    static final String password = "szhrayxkgzrqymne";

    //Properties: Khai bao cac thuoc tinh
    public static boolean sendEmail(String to, String tieuDe, String noiDung) {
        Properties props = new Properties();

        //tìm hiểu smtp, host
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP HOST
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // create Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // TODO Auto-generated method stub
                return new PasswordAuthentication(from, password);
            }
        };


        //Phiên làm việc
        Session session = Session.getInstance(props, auth);


        //Tạo một tin nhắn
        MimeMessage msg = new MimeMessage(session);

        try {
            //Kiểu nội dung
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");

            //Người gửi
            msg.setFrom(from);

            //Nguời nhận
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            //Tiêu đề  của email
            msg.setSubject(tieuDe);

            //Quy định ngày gửi
            msg.setSentDate(new Date());

            //Quy định email nhận phản hồi
            // msg.setReplyTo(InternetAddress.parse(from,false));

            //Nội dung
            msg.setContent(noiDung, "text/HTML; charset=UTF-8");


            //Gửi email
            Transport.send(msg);
            System.out.println("Gửi email thành công");
            return true;

        } catch (Exception e) {
            System.out.println("Gặp lỗi trong quá trình gửi email");
            e.printStackTrace();
            return false;
        }
    }

}