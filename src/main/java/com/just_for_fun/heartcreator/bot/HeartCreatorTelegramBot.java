package com.just_for_fun.heartcreator.bot;

import com.just_for_fun.heartcreator.bot.config.BotConfig;
import com.just_for_fun.heartcreator.bot.config.client.TelegramClient;
import com.just_for_fun.heartcreator.bot.handlers.MessageHandler;
import com.just_for_fun.heartcreator.bot.handlers.commands.Command;
import com.just_for_fun.heartcreator.bot.handlers.commands.StartCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
@Slf4j
public class HeartCreatorTelegramBot extends TelegramWebhookBot {

    private final BotConfig config;
    private final ApplicationContext ctx;
    private final MessageHandler messageHandler;

    public HeartCreatorTelegramBot(BotConfig config, TelegramClient telegramClient, MessageHandler messageHandler, ApplicationContext ctx) {
        this.config = config;
        this.messageHandler = messageHandler;
        this.ctx = ctx;
        log.info(telegramClient.setWebhook(getBotToken(), getBotPath()).toString());
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update);
        } catch (TelegramApiException e) {
            log.error("Error while executing message. Exception " + e.getMessage());
            return new SendMessage(update.getMessage().getChatId().toString(), "Something went wrong");
        }
    }

    public BotApiMethod<?> handleUpdate(Update update) throws TelegramApiException {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getFrom().getId();
            Optional<Command> command = determineCommand(message.getText());
            if (command.isPresent()) {
                execute(command.get().execute(chatId));
            } else {
                messageHandler.handle(update.getMessage(), this);
            }
        }
        return null;
    }

    private Optional<Command> determineCommand(String command) {
        return command.equals("/start") ? Optional.of(ctx.getBean("startCommand", StartCommand.class))
                : Optional.empty();
    }

    @Override
    public String getBotPath() {
        return config.getPath();
    }

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
