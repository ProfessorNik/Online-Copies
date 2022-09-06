package ru.nsu.onlinecopiesofyourself.infrastructure.controller;

import org.json.JSONException;
import ru.nsu.onlinecopiesofyourself.application.model.MessageInfo;
import ru.nsu.onlinecopiesofyourself.application.usecase.GenerateMessage;
import ru.nsu.onlinecopiesofyourself.application.usecase.ProcessMessage;
import ru.nsu.onlinecopiesofyourself.domain.exception.InvalidMessageException;

public record MessageForwardingController(GenerateMessage generateMessage, ProcessMessage processMessage) {
    public void receiveMessage(MessageInfo message) {
        try {
            processMessage.execute(message);
        } catch (InvalidMessageException | JSONException e) {
            System.out.println(e.getMessage());
        }

    }

    public String sendMessage() {
        return generateMessage.execute();
    }
}
