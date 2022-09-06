package ru.nsu.onlinecopiesofyourself.config;

import ru.nsu.onlinecopiesofyourself.application.usecase.CleanExpiredOnlineCopies;
import ru.nsu.onlinecopiesofyourself.application.usecase.impl.CleanExpiredOnlineCopiesImpl;
import ru.nsu.onlinecopiesofyourself.application.usecase.impl.GenerateMessageImpl;
import ru.nsu.onlinecopiesofyourself.application.usecase.impl.GetOnlineInfoImpl;
import ru.nsu.onlinecopiesofyourself.application.usecase.impl.ProcessMessageImpl;
import ru.nsu.onlinecopiesofyourself.application.util.Observable;
import ru.nsu.onlinecopiesofyourself.infrastructure.controller.CleanOnlineCopiesController;
import ru.nsu.onlinecopiesofyourself.infrastructure.controller.MessageForwardingController;
import ru.nsu.onlinecopiesofyourself.infrastructure.controller.OnlineInfoController;
import ru.nsu.onlinecopiesofyourself.infrastructure.network.Transceiver;
import ru.nsu.onlinecopiesofyourself.infrastructure.repository.CopiesOfYourselfRepository;
import ru.nsu.onlinecopiesofyourself.infrastructure.runnable.Cleaner;
import ru.nsu.onlinecopiesofyourself.infrastructure.runnable.Receiver;
import ru.nsu.onlinecopiesofyourself.infrastructure.runnable.Sender;
import ru.nsu.onlinecopiesofyourself.infrastructure.view.AppView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OnlineCopiesOfYourselfConfig {
    private final Thread receiveThread;
    private final ScheduledExecutorService sendExecutor;
    private final ScheduledExecutorService cleanExecutor;
    private final Sender sender;
    private final Cleaner cleaner;
    private final Transceiver transceiver;
    private final AppView appView;

    public OnlineCopiesOfYourselfConfig(){
        var observableView = new Observable();
        var repository = new CopiesOfYourselfRepository();
        var getOnlineInfo = new GetOnlineInfoImpl(repository);
        var onlineInfoController = new OnlineInfoController(getOnlineInfo);
        appView = new AppView(onlineInfoController);
        observableView.addListener(appView);
        var generateMessage = new GenerateMessageImpl();
        var processMessage = new ProcessMessageImpl(repository, observableView);
        var cleanExpiredOnlineCopies = new CleanExpiredOnlineCopiesImpl(repository, observableView);
        var messageForwardingController = new MessageForwardingController(generateMessage, processMessage);
        var cleanOnlineCopiesController = new CleanOnlineCopiesController(cleanExpiredOnlineCopies);
        transceiver = new Transceiver();
        var receiver = new Receiver(transceiver, messageForwardingController);
        cleaner = new Cleaner(cleanOnlineCopiesController);
        sender = new Sender(transceiver, messageForwardingController);
        receiveThread = new Thread(receiver);
        sendExecutor = Executors.newSingleThreadScheduledExecutor();
        cleanExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    public void startApplication(){
        receiveThread.start();
        sendExecutor.scheduleWithFixedDelay(sender,
                0,
                AppProperties.getInit().getTimeSendMilliseconds(),
                TimeUnit.MILLISECONDS);
        cleanExecutor.scheduleWithFixedDelay(cleaner,
                0,
                AppProperties.getInit().getTimeCleanMilliseconds(),
                TimeUnit.MILLISECONDS);
        appView.setVisible(true);
    }
}
