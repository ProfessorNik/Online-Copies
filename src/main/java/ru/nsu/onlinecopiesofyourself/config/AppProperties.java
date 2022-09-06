package ru.nsu.onlinecopiesofyourself.config;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

public class AppProperties {
    private static AppProperties init;
    Properties properties;

    private final String hostname;
    private final int port;
    private final int bufferSize;

    private final long timeExpiredMilliseconds;
    private final long timeSendMilliseconds;
    private final long timeCleanMilliseconds;

    private AppProperties(Properties properties) {
        this.properties = properties;
        hostname = properties.getProperty("hostname");
        port = Integer.parseInt(properties.getProperty("port"));
        bufferSize = Integer.parseInt(properties.getProperty("buffer.size"));
        timeExpiredMilliseconds = Long.parseLong(properties.getProperty("time.expired.milliseconds"));
        timeSendMilliseconds = Long.parseLong(properties.getProperty("time.send.milliseconds"));
        timeCleanMilliseconds = Long.parseLong(properties.getProperty("time.clean.milliseconds"));
    }

    public static AppProperties getInit() {
        if(init == null){
            Properties properties = new Properties();
            try {
                properties.load(AppProperties.class.getClassLoader().getResourceAsStream("app.properties"));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            init = new AppProperties(properties);
            return init;
        }
        return init;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public long getTimeExpiredMilliseconds() {
        return timeExpiredMilliseconds;
    }

    public long getTimeSendMilliseconds() {
        return timeSendMilliseconds;
    }

    public long getTimeCleanMilliseconds() {
        return timeCleanMilliseconds;
    }
}
