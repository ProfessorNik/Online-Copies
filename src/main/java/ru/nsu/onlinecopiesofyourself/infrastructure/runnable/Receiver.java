package ru.nsu.onlinecopiesofyourself.infrastructure.runnable;


import ru.nsu.onlinecopiesofyourself.infrastructure.controller.MessageForwardingController;
import ru.nsu.onlinecopiesofyourself.infrastructure.network.Transceiver;

public class Receiver implements Runnable{
    public final Transceiver transceiver;
    public final MessageForwardingController controller;

    public Receiver(Transceiver transceiver, MessageForwardingController controller) {
        this.transceiver = transceiver;
        this.controller = controller;
    }

    @Override
    public void run() {
        while (true) {
            var message = transceiver.receiveMassage();
            controller.receiveMessage(message);
        }
    }
}
