package ru.nsu.onlinecopiesofyourself.application.usecase.impl;

import ru.nsu.onlinecopiesofyourself.application.model.OnlineInfo;
import ru.nsu.onlinecopiesofyourself.application.usecase.GetOnlineInfo;
import ru.nsu.onlinecopiesofyourself.infrastructure.repository.CopiesOfYourselfRepository;

public class GetOnlineInfoImpl implements GetOnlineInfo {
    private final CopiesOfYourselfRepository repository;

    public GetOnlineInfoImpl(CopiesOfYourselfRepository repository) {
        this.repository = repository;
    }

    @Override
    public OnlineInfo execute() {
        return new OnlineInfo(repository.size(), repository.findAllIp());
    }
}
