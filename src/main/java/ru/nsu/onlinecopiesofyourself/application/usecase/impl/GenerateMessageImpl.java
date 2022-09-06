package ru.nsu.onlinecopiesofyourself.application.usecase.impl;

import org.json.JSONObject;
import ru.nsu.onlinecopiesofyourself.application.usecase.GenerateMessage;
import ru.nsu.onlinecopiesofyourself.domain.entity.CopyOfYourself;
import ru.nsu.onlinecopiesofyourself.domain.value.MessageGenerator;

import java.util.List;

public class GenerateMessageImpl implements GenerateMessage {
    private final MessageGenerator messageGenerator;

    public GenerateMessageImpl() {
        messageGenerator = new MessageGenerator();
    }

    @Override
    public String execute() {
        return messageGenerator.generateMessage();
    }
}
