//package vn.edu.fpt.notification.service.impl;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
///**
// * @author : Hoang Lam
// * @product : Charity Management System
// * @project : Charity System
// * @created : 06/09/2022 - 19:30
// * @contact : 0834481768 - hoang.harley.work@gmail.com
// **/
//@Component
//@Slf4j
//public class TelegramBotService extends TelegramLongPollingBot {
//
//    private String username;
//    private String token;
//    private Long chatId;
//
//    public TelegramBotService(Environment environment) {
//        this.username = environment.getProperty("app.telegram.notification.username");
//        this.token = environment.getProperty("app.telegram.notification.token");
//        this.chatId = environment.getProperty("app.telegram.notification.chat-id", Long.class);
//    }
//
//
//    @Override
//    public String getBotUsername() {
//        return username;
//    }
//
//    @Override
//    public String getBotToken() {
//        return token;
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
//            message.setChatId(update.getMessage().getChatId().toString());
//            message.setText(update.getMessage().getText());
//
//            try {
//                execute(message); // Call method to send the message
//                log.info("Reply message to telegram success");
//            } catch (TelegramApiException e) {
//                log.info("Can't execute message to telegram: {}", e.getMessage());
//            }
//        }
//    }
//
//    public void sendMessage(String text, String customChatId){
//        SendMessage message = new SendMessage();
//        message.setChatId(customChatId);
//        message.setText(text);
//        try {
//            execute(message);
//            log.info("Send message to telegram success");
//        }catch (TelegramApiException ex){
//            log.info("Can't send message to telegram with chatId: {}, message: {}", chatId, message);
//        }
//    }
//}
