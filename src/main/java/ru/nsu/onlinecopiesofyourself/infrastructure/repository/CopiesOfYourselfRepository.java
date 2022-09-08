package ru.nsu.onlinecopiesofyourself.infrastructure.repository;

import ru.nsu.onlinecopiesofyourself.domain.entity.CopyOfYourself;
import ru.nsu.onlinecopiesofyourself.domain.value.Message;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CopiesOfYourselfRepository {
    private final List<CopyOfYourself> copiesOfYourself;

    public CopiesOfYourselfRepository() {
        this.copiesOfYourself = new ArrayList<>();
    }

    public synchronized List<String> findAllIp(){
        return copiesOfYourself.stream().map(CopyOfYourself::getIp).collect(Collectors.toList());
    }
    public synchronized Optional<CopyOfYourself> findById(UUID id){
        for (var copyOfYourself: copiesOfYourself) {
            if(copyOfYourself.getId().equals(id)){
                return Optional.of(copyOfYourself);
            }
        }
        return Optional.empty();
    }

    public synchronized void add(CopyOfYourself copyOfYourself){
        copiesOfYourself.add(copyOfYourself);
    }

    public synchronized void ifPresentWithIdOrElse(UUID id, Consumer<CopyOfYourself> consumer, Runnable runnable) {
        findById(id).ifPresentOrElse(consumer, runnable);
    }

    public synchronized void addIfPresentOrCreate(UUID id, Message message, String senderIp){
        findById(id).ifPresentOrElse(copyOfYourself -> copyOfYourself.addMessageId(message.getMessageId()),
                () -> add(new CopyOfYourself(message.getAppId(), senderIp, message.getMessageId())));
    }

    public synchronized int size(){
        return copiesOfYourself.size();
    }

    public synchronized void deleteByExpiredTime(long expirationTime){
        copiesOfYourself.removeIf(word -> word.hasTimeExpired(expirationTime));
    }
}
