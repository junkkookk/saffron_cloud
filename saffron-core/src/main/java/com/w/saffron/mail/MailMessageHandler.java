package com.w.saffron.mail;


import com.w.saffron.system.constant.MailCons;
import com.w.saffron.system.constant.MailMessageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author w
 * @since 2023/3/30
 */
@Component
@Slf4j
public class MailMessageHandler {

    private final MailHelper mailHelper;

    @Autowired
    public MailMessageHandler(MailHelper mailHelper) {
        this.mailHelper = mailHelper;
    }


    @RabbitHandler
    @RabbitListener(queues = MailCons.DIRECT_MAIL_QUEUE)
    public void mailMessageHandle(MailMessageBean messageBean){
        log.info("接收消息:{}-{}", messageBean.getSubject(),messageBean.getTo());
        mailHelper.sendHtmlMail(messageBean);
    }

    @Bean
    public Queue directMailQueue() {
        return new Queue(MailCons.DIRECT_MAIL_QUEUE);
    }

}
