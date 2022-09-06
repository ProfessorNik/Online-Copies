package ru.nsu.onlinecopiesofyourself.infrastructure.runnable;

import ru.nsu.onlinecopiesofyourself.infrastructure.controller.CleanOnlineCopiesController;

public class Cleaner implements Runnable{
    private final CleanOnlineCopiesController controller;

    public Cleaner(CleanOnlineCopiesController controller) {
        this.controller = controller;
    }


    @Override
    public void run() {
        controller.clean();
    }
}
