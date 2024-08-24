package ru.min4j.services.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.min4j.services.ConsumerServices;
import ru.min4j.services.ProducerServices;

import static ru.min4j.model.RabbitQueue.TEXT_MESSAGE_UPDATE;

@Service
@Log4j
public class ConsumerServiceImpl implements ConsumerServices {
    private final ProducerServices producerService;

    public ConsumerServiceImpl(ProducerServices producerService) {
        this.producerService = producerService;
    }

    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessageUpdate(Update update) {
        log.debug("NODE: text message is received");

        var message = update.getMessage();
        SendMessage sendMessage = SendMessage.builder()
                .chatId(message.getChatId())
                .text("FROM NODE: " + message.getText())
                .build();
        producerService.producerAnswer(sendMessage);
    }
}
