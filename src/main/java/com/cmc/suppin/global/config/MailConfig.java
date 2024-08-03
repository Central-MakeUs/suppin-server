package com.cmc.suppin.global.config;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MailConfig {
    private final JavaMailSender javaMailSender;

    public boolean sendMail(String toEmail, String code) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(new InternetAddress("suppindev@gmail.com", "Suppin", "UTF-8"));
            helper.setTo(toEmail);
            helper.setSubject("Suppin 인증번호");

            String emailBody = String.format(
                    "<!DOCTYPE html>" +
                            "<html lang=\"en\">" +
                            "<head>" +
                            "<meta charset=\"UTF-8\">" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                            "<title>Suppin Email Verification</title>" +
                            "</head>" +
                            "<body style=\"font-family: Arial, sans-serif;\">" +
                            "<div style=\"padding: 20px; border: 1px solid #eaeaea; max-width: 600px; margin: 0 auto;\">" +
                            "<div style=\"padding: 20px; border-bottom: 1px solid #eaeaea; text-align: center;\">" +
                            "<img src=\"cid:suppinLogo\" alt=\"Suppin Logo\" style=\"width: 100px;\">" +
                            "<h2 style=\"color: #333;\"><span style=\"color: #1a73e8;\">[Suppin]</span> 인증번호를 안내해 드립니다.</h2>" +
                            "</div>" +
                            "<div style=\"padding: 20px;\">" +
                            "<p>안녕하세요, Suppin을 이용해주셔서 감사합니다 :)</p>" +
                            "<p>Suppin 회원가입을 위해 인증번호를 안내해 드립니다. 아래 인증번호를 입력하여 이메일 인증을 완료해 주세요.</p>" +
                            String.format("<div style=\"font-size: 24px; font-weight: bold; margin: 20px 0; color: #333; text-align: center;\">%s</div>", code) +
                            "<table style=\"width: 100%; border-collapse: collapse;\">" +
                            "<tbody>" +
                            "<tr><td style=\"padding: 10px; border: 1px solid #eaeaea;\">인증 번호</td><td style=\"padding: 10px; border: 1px solid #eaeaea;\">" + code + "</td></tr>" +
                            "<tr><td style=\"padding: 10px; border: 1px solid #eaeaea;\">요청 일시</td><td style=\"padding: 10px; border: 1px solid #eaeaea;\">" + LocalDateTime.now().toString() + "</td></tr>" +
                            "</tbody>" +
                            "</table>" +
                            "</div>" +
                            "<div style=\"padding: 20px; border-top: 1px solid #eaeaea; text-align: center; color: #999;\">" +
                            "<p>감사합니다.</p>" +
                            "<p style=\"font-size: 12px;\">※ 본 메일은 Suppin 서비스 이용에 관한 안내 메일입니다.</p>" +
                            "</div>" +
                            "</div>" +
                            "</body>" +
                            "</html>"
            );

            helper.setText(emailBody, true);

            // Add inline image
            ClassPathResource logoImage = new ClassPathResource("static/images/suppin-logo.png");
            helper.addInline("suppinLogo", logoImage);

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}


