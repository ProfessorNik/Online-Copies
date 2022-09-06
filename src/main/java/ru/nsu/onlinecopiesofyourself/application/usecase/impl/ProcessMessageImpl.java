package ru.nsu.onlinecopiesofyourself.application.usecase.impl;

import ru.nsu.onlinecopiesofyourself.application.model.MessageInfo;
import ru.nsu.onlinecopiesofyourself.application.usecase.ProcessMessage;
import ru.nsu.onlinecopiesofyourself.application.util.Observable;
import ru.nsu.onlinecopiesofyourself.domain.entity.CopyOfYourself;
import ru.nsu.onlinecopiesofyourself.domain.exception.InvalidMessageException;
import ru.nsu.onlinecopiesofyourself.domain.value.JSONMessageParser;
import ru.nsu.onlinecopiesofyourself.infrastructure.repository.CopiesOfYourselfRepository;

public class ProcessMessageImpl implements ProcessMessage {
    private final CopiesOfYourselfRepository copiesOfYourselfRepository;
    private final Observable observableView;

    public ProcessMessageImpl(CopiesOfYourselfRepository copiesOfYourselfRepository, Observable observableView) {
        this.copiesOfYourselfRepository = copiesOfYourselfRepository;
        this.observableView = observableView;
    }

    @Override
    public void execute(MessageInfo message) {
        var messageParser = JSONMessageParser.from(message.getContent());

        int oldSize = copiesOfYourselfRepository.size();

        checkPhrase(messageParser);
        markMessage(messageParser, message.getSenderId());

        if(oldSize != copiesOfYourselfRepository.size()) {
            observableView.notifyListeners();
        }
    }

    private void checkPhrase(JSONMessageParser messageParser){
        if(!messageParser.getPhrase().equals(messageParser.getPhrase())){
            throw new InvalidMessageException("Invalid message phrase exception");
        }
    }

    private void markMessage(JSONMessageParser messageParser, String senderIp){
        copiesOfYourselfRepository.ifPresentWithIdOrElse(messageParser.getAppId(),
                copyOfYourself -> copyOfYourself.addMessageId(messageParser.getMessageId()),
                () -> copiesOfYourselfRepository.add(new CopyOfYourself(messageParser.getAppId(), senderIp, messageParser.getMessageId())));
    }
}
