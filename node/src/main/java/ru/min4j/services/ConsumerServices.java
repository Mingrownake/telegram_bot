package ru.min4j.services;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumerServices {
    void consumeTextMessageUpdate(Update update);
}
