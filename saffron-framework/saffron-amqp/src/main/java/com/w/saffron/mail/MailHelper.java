package com.w.saffron.mail;

import cn.hutool.extra.spring.SpringUtil;
import com.w.saffron.dto.MailMessageDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author w
 * @since 2023/3/27
 */
@Service
public class MailHelper {

    public  void sendHtmlMail(MailMessageDto messageBean) {
        JavaMailSender mailSender = SpringUtil.getBean(JavaMailSender.class);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("850338501@qq.com");
            helper.setTo(messageBean.getTo());
            helper.setSubject(messageBean.getSubject());
            helper.setText(messageBean.getContent(), true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        mailSender.send(message);
    }
}
