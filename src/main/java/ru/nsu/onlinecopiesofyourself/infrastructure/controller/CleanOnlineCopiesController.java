package ru.nsu.onlinecopiesofyourself.infrastructure.controller;

import ru.nsu.onlinecopiesofyourself.application.usecase.CleanExpiredOnlineCopies;

public class CleanOnlineCopiesController {
    private final CleanExpiredOnlineCopies cleanExpiredOnlineCopies;

    public CleanOnlineCopiesController(CleanExpiredOnlineCopies cleanExpiredOnlineCopies) {
        this.cleanExpiredOnlineCopies = cleanExpiredOnlineCopies;
    }

    public void clean() {
        cleanExpiredOnlineCopies.execute();
    }
}
