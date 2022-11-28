package vn.edu.fpt.notification.config.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.fpt.notification.service.SmsService;

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
public class SendSmsConsumer extends Consumer{

    private final SmsService smsService;

    @Override
    protected void listen(String value, String topic, String key) {
        super.listen(value, topic, key);
        smsService.sendSms(value);
    }
}
