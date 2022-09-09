package ru.nsu.onlinecopiesofyourself.infrastructure.runnable;

import ru.nsu.onlinecopiesofyourself.infrastructure.controller.MessageForwardingController;
import ru.nsu.onlinecopiesofyourself.infrastructure.network.Transceiver;

public class Sender implements Runnable{
    private final Transceiver transceiver;
    private final MessageForwardingController controller;

    public Sender(Transceiver transceiver, MessageForwardingController controller) {
        this.transceiver = transceiver;
        this.controller = controller;
    }

    @Override
    public void run() {
        String message = controller.generateMessage();
        transceiver.sendMessage(message);
    }
}
