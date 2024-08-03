package com.cmc.suppin.global.config;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailConfig {
    private final JavaMailSender javaMailSender;

    public boolean sendMail(String toEmail, String code) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setFrom(new InternetAddress("suppindev@gmail.com", "Suppin", "UTF-8"));
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setSubject("Suppin 인증번호"); // 제목
            helper.setTo(toEmail); // 받는사람

            String emailBody = String.format("Suppin 회원가입 시 이메일 인증번호는 %s입니다.", code);
            helper.setText(emailBody, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
