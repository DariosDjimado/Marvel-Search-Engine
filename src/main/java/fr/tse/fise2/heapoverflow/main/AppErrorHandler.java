package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.gui.UI;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public final class AppErrorHandler {
    private final static Logger LOGGER = Logger.getLogger(AppErrorHandler.class);
    private static UI ui;

    private AppErrorHandler() {
    }

    public static void onError(Exception e) {
        e.printStackTrace();
        LOGGER.error(e.getMessage());
        //e.printStackTrace();
        // ui.getUiBottomComponent().displayErrorPopup(e.getMessage());
    }

    public static void onInfo(String info) {
        LOGGER.info(info);
    }

    public static void setUi(UI ui) {
        AppErrorHandler.ui = ui;
    }

    public static void configureLogging() {
        ConsoleAppender consoleAppender = new ConsoleAppender();
        String pattern = "%c{1}:%L %d{yyyy-MM-dd HH:mm:ss} %-5p - %m%n";
        consoleAppender.setLayout(new PatternLayout(pattern));
        consoleAppender.setThreshold(Level.ERROR);
        consoleAppender.activateOptions();
        Logger.getRootLogger().addAppender(consoleAppender);
    }
}
