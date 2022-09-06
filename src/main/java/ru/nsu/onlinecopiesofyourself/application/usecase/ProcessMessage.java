package ru.nsu.onlinecopiesofyourself.application.usecase;

import ru.nsu.onlinecopiesofyourself.application.model.MessageInfo;

public interface ProcessMessage {
    void execute(MessageInfo message);
}
