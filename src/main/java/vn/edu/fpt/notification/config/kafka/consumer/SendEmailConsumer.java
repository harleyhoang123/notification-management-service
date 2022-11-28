package vn.edu.fpt.notification.config.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vn.edu.fpt.notification.service.EmailService;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/11/2022 - 09:23
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class SendEmailConsumer extends Consumer{

    private final EmailService emailService;

    @Override
    @KafkaListener(id = "sendEmailConsumer", topics = "flab.send_email", groupId = "notification_group")
    protected void listen(String value, String topic, String key) {
        super.listen(value, topic, key);
        emailService.sendEmail(value);
    }
}
