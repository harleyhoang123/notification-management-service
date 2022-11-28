package vn.edu.fpt.notification.config.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.exception.BusinessException;
import vn.edu.fpt.notification.service.impl.TelegramBotService;

import javax.annotation.PostConstruct;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/09/2022 - 20:18
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class RegisterTelegramBot {

    private final TelegramBotsApi telegramBotsApi;
    private final Environment environment;

    @PostConstruct
    public void registerBotsApi(){
        try {
            telegramBotsApi.registerBot(new TelegramBotService(environment));
        } catch (TelegramApiException e) {
            log.error("Can't register telegram bot: {}", e.getMessage());
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
