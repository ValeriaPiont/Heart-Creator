package com.just_for_fun.heartcreator.bot.handlers.commands;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component(value = "startCommand")
public class StartCommand implements Command {

    @Override
    public SendMessage execute(Long chatId) {
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text("Hi! I create heart with emoji. Send me two emoji. Format: emoji1-emoji2")
                .build();
    }

}