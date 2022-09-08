package ru.nsu.onlinecopiesofyourself.application.usecase.impl;

import ru.nsu.onlinecopiesofyourself.application.model.MessageInfo;
import ru.nsu.onlinecopiesofyourself.application.usecase.ProcessMessage;
import ru.nsu.onlinecopiesofyourself.application.util.Observable;
import ru.nsu.onlinecopiesofyourself.domain.entity.CopyOfYourself;
import ru.nsu.onlinecopiesofyourself.domain.exception.InvalidMessageException;
import ru.nsu.onlinecopiesofyourself.domain.value.Message;
import ru.nsu.onlinecopiesofyourself.infrastructure.repository.CopiesOfYourselfRepository;

public class ProcessMessageImpl implements ProcessMessage {
    private final CopiesOfYourselfRepository copiesOfYourselfRepository;
    private final Observable observableView;

    public ProcessMessageImpl(CopiesOfYourselfRepository copiesOfYourselfRepository, Observable observableView) {
        this.copiesOfYourselfRepository = copiesOfYourselfRepository;
        this.observableView = observableView;
    }

    @Override
    public void execute(MessageInfo messageInfo) {
        var message = messageInfo.getContent();
        int oldSize = copiesOfYourselfRepository.size();

        checkPhrase(message);
        markMessage(message, messageInfo.getSenderId());

        if(oldSize != copiesOfYourselfRepository.size()) {
            observableView.notifyListeners();
        }
    }

    private void checkPhrase(Message message){
        if(!"Hello!".equals(message.getPhrase())){
            throw new InvalidMessageException("Invalid phrase in message: " + message.getPhrase());
        }
    }

    private void markMessage(Message message, String senderIp){
        copiesOfYourselfRepository.ifPresentWithIdOrElse(message.getAppId(),
                copyOfYourself -> copyOfYourself.addMessageId(message.getMessageId()),
                () -> copiesOfYourselfRepository.add(new CopyOfYourself(message.getAppId(), senderIp, message.getMessageId())));
    }
}
