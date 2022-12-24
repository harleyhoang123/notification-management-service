package vn.edu.fpt.notification.config.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vn.edu.fpt.notification.service.NotificationService;


/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 23/12/2022 - 14:05
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@RequiredArgsConstructor
public class HandleNotifyConsumer extends Consumer{

    private final NotificationService notificationService;

    @Override
    @KafkaListener(id = "handleNotifyConsumer", topics = "flab.notification.handle", groupId = "notification_handle_group")
    protected void listen(String value, String topic, String key) {
        super.listen(value, topic, key);
        notificationService.addNotify(value);
    }
}
