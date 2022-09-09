package ru.nsu.onlinecopiesofyourself.infrastructure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.onlinecopiesofyourself.application.model.MessageInfo;
import ru.nsu.onlinecopiesofyourself.application.usecase.GenerateMessage;
import ru.nsu.onlinecopiesofyourself.application.usecase.ProcessMessage;
import ru.nsu.onlinecopiesofyourself.domain.exception.InvalidMessageException;
import ru.nsu.onlinecopiesofyourself.domain.value.Message;
import ru.nsu.onlinecopiesofyourself.infrastructure.dto.MessageDto;

public class MessageForwardingController {
    private final GenerateMessage generateMessage;
    private final ProcessMessage processMessage;

    private final ObjectMapper mapper = new ObjectMapper();

    public MessageForwardingController(GenerateMessage generateMessage, ProcessMessage processMessage) {
        this.generateMessage = generateMessage;
        this.processMessage = processMessage;
    }

    private MessageInfo mapFromDto(MessageDto dto) throws JsonProcessingException {
        return new MessageInfo(mapper.readValue(dto.content(), Message.class), dto.ip());
    }

    public void processMessage(MessageDto message) {
        try {
            processMessage.execute(mapFromDto(message));
        } catch (InvalidMessageException | JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

    }

    public String generateMessage() {
        return generateMessage.execute();
    }
}
