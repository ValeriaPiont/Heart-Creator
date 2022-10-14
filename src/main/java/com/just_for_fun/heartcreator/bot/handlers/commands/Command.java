package com.just_for_fun.heartcreator.bot.handlers.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Command {
    SendMessage execute(Long chatId);
}