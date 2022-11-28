package vn.edu.fpt.notification;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import vn.edu.fpt.notification.config.kafka.producer.SendEmailProducer;
import vn.edu.fpt.notification.config.sms.SmsConfigProperties;
import vn.edu.fpt.notification.dto.event.SendEmailEvent;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@EnableConfigurationProperties(SmsConfigProperties.class)
public class NotificationServiceApplication implements CommandLineRunner {

    @Autowired
    private SendEmailProducer producer;

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        producer.sendMessage(SendEmailEvent.builder()
                        .templateId("534534")
                        .sendTo("hoanglammaster@gmail.com")
                        .cc("harley.hoang.work@gmail.com")
                        .bcc("lamhvhe@gmail.com")
                        .params(null)
                .build());
    }
}
