package ru.min4j.controllers;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.min4j.services.UpdateProducer;
import ru.min4j.utils.MessageUtils;

@Controller
@Log4j
public class UpdateController {
    private TelegramBot telegramBot;
    private final MessageUtils messageUtils;
    private UpdateProducer updateProducer;

    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Update is null");
            return;
        }

        if (update.getMessage() != null) {
            distributeMessageByType(update);
        } else {
            log.error("Message has unsupported type");
        }
    }

    private void distributeMessageByType(Update update) {
        var message = update.getMessage();
        if (message.getText() != null) {
            processTextMessage(update);
        } else if (message.getDocument() != null) {
            processDocumentMessage(update);
        } else {
            setUnsupportedTypeMessageView(update);
        }
    }

    private void processTextMessage(Update update) {
        updateProducer.produce("TEXT_MESSAGE_UPDATE", update);
    }

    private void processDocumentMessage(Update update) {

    }

    private void setUnsupportedTypeMessageView(Update update) {

    }

    public void setView(SendMessage message) {
        telegramBot.sendAnswerMessage(message);
    }
}
