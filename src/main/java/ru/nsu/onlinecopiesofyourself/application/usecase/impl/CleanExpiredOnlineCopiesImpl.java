package ru.nsu.onlinecopiesofyourself.application.usecase.impl;

import ru.nsu.onlinecopiesofyourself.application.usecase.CleanExpiredOnlineCopies;
import ru.nsu.onlinecopiesofyourself.application.util.Observable;
import ru.nsu.onlinecopiesofyourself.config.AppProperties;
import ru.nsu.onlinecopiesofyourself.infrastructure.repository.CopiesOfYourselfRepository;

import java.util.Date;

public class CleanExpiredOnlineCopiesImpl implements CleanExpiredOnlineCopies {
    private final CopiesOfYourselfRepository repository;
    private final Observable observableView;

    public CleanExpiredOnlineCopiesImpl(CopiesOfYourselfRepository repository, Observable observableView) {
        this.repository = repository;
        this.observableView = observableView;
    }

    @Override
    public void execute() {
        int oldSize = repository.size();
        repository.deleteByExpiredTime(new Date(AppProperties.getInit().getTimeExpiredMilliseconds()));

        if(oldSize != repository.size()) {
            observableView.notifyListeners();
        }
    }
}
