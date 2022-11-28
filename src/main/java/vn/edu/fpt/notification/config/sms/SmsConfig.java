package vn.edu.fpt.notification.config.sms;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 07/09/2022 - 10:08
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Configuration
public class SmsConfig {

    @Autowired
    private SmsConfigProperties properties;

    @Bean
    public void initTwilio(){
        Twilio.init(properties.getAccountSid(), properties.getAuthToken());
    }
}
