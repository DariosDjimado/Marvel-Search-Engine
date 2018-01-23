package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.gui.UI;
import org.apache.log4j.*;

import java.io.IOException;

public final class AppErrorHandler {
    private final static Logger LOGGER = Logger.getLogger(AppErrorHandler.class);
    private static UI userInterface;

    private AppErrorHandler() {
    }

    public static void onError(Exception e) {
        e.printStackTrace();
        if (userInterface != null) {
            userInterface.getUiBottomComponent().addNewError(e);
        }
        LOGGER.error(e.getMessage());
    }

    public static void setUserInterface(UI userInterface) {
        AppErrorHandler.userInterface = userInterface;
    }

    public static void configureLogging() {
        try {
            String pattern = "%c{1}:%L %d{yyyy-MM-dd HH:mm:ss} %-5p - %m%n";
            FileAppender fileAppender = new FileAppender(new PatternLayout(), "log.log");
            fileAppender.setLayout(new PatternLayout(pattern));
            fileAppender.activateOptions();
            fileAppender.setThreshold(Level.INFO);
            Logger.getRootLogger().addAppender(fileAppender);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
