package vn.edu.fpt.notification.config.sms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 07/09/2022 - 10:10
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@ConfigurationProperties(prefix = "app.sms")
@Setter
@Getter
public class SmsConfigProperties {

    private String accountSid;
    private String authToken;
    private String serviceSid;
}
