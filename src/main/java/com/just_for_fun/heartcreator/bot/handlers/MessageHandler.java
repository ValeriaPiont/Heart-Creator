package com.just_for_fun.heartcreator.bot.handlers;

import com.just_for_fun.heartcreator.backend.HeartCreator;
import com.just_for_fun.heartcreator.backend.exception.ParseException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MessageHandler {

    private final HeartCreator heartCreator;

    public MessageHandler(HeartCreator heartCreator) {
        this.heartCreator = heartCreator;
    }

    public void handle(Message message, AbsSender sender) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        try {
            String heartString = heartCreator.getHeart(message.getText());
            sendMessage.setText(heartString);
        } catch (ParseException e) {
            sendMessage.setText(e.getMessage() + " Format: emoji1-emoji2");
        }

        try {
            sender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
