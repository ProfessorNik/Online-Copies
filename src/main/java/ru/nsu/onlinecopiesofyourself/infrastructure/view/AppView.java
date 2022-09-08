package ru.nsu.onlinecopiesofyourself.infrastructure.view;

import ru.nsu.onlinecopiesofyourself.application.model.OnlineInfo;
import ru.nsu.onlinecopiesofyourself.application.util.Observer;
import ru.nsu.onlinecopiesofyourself.infrastructure.controller.OnlineInfoController;

import javax.swing.*;
import java.awt.*;

public class AppView extends JFrame implements Observer {
    private final JEditorPane online;
    private final OnlineInfoController controller;

    private static final int width = 300;
    private static final int height = 300;

    public AppView(OnlineInfoController controller) {
        this.controller = controller;
        var mainPanel = new JPanel();
        online = new JEditorPane();
        online.setText("Online: 0");
        online.setEditable(false);
        online.setSize(new Dimension(250,40));

        mainPanel.add(online);
        setContentPane(mainPanel);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setBounds(((int)toolkit.getScreenSize().getWidth() - width) / 2,
                ((int)toolkit.getScreenSize().getHeight() - height) / 2,
                width,
                height);
        setContentPane(mainPanel);
    }

    public void update(){
        SwingUtilities.invokeLater(()->{
            OnlineInfo onlineInfo = controller.getInfo();
            String text = String.join("\n", onlineInfo.getIps());
            online.setText("Online: " + onlineInfo.getOnline() + "\n" + text);
        });
    }

}
