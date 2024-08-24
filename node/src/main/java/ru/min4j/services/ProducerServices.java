package ru.min4j.services;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ProducerServices {
    void producerAnswer(SendMessage sendMessage);
}
