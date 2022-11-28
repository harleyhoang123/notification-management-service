package vn.edu.fpt.notification.config.telegram;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.exception.BusinessException;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/09/2022 - 19:49
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Configuration
public class TelegramBotConfig {

    @Bean
    public TelegramBotsApi configTelegramBot(){
        try {
           return new TelegramBotsApi(DefaultBotSession.class);
        } catch (TelegramApiException ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't config telegram bot api: "+ ex.getMessage());
        }
    }
}
