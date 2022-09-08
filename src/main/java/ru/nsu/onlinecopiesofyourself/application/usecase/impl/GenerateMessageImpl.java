package ru.nsu.onlinecopiesofyourself.application.usecase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.onlinecopiesofyourself.application.usecase.GenerateMessage;
import ru.nsu.onlinecopiesofyourself.domain.exception.InvalidMessageException;
import ru.nsu.onlinecopiesofyourself.domain.value.Message;

import java.util.UUID;

public class GenerateMessageImpl implements GenerateMessage {
    public static final String phrase = "Hello!";
    public static final UUID appId = UUID.randomUUID();

    private final ObjectMapper mapper;

    public GenerateMessageImpl() {
        mapper = new ObjectMapper();
    }

    @Override
    public String execute() {
        try {
            return mapper.writeValueAsString(Message.fromUUIDs(phrase, appId, UUID.randomUUID()));
        } catch (JsonProcessingException e){
            throw new InvalidMessageException("message was generated incorrectly", e);
        }
    }
}
