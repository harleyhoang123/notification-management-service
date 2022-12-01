package vn.edu.fpt.notification.config.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vn.edu.fpt.notification.service.TelegramService;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/11/2022 - 09:22
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class SendTelegramConsumer extends Consumer{

    private final TelegramService telegramService;

    @Override
    @KafkaListener(id = "sendTelegramConsumer", topics = "flab.notification.send_telegram", groupId = "notification_group")
    protected void listen(String value, String topic, String key) {
        super.listen(value, topic, key);
        telegramService.sendNotification(value);
    }
}
