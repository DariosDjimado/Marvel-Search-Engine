package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.interfaces.LoggerObserver;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public final class AppLogger implements LoggerObserver {
    final static Logger LOGGER = Logger.getLogger(AppLogger.class);

    public void configureLogging() {
        ConsoleAppender consoleAppender = new ConsoleAppender();
        String pattern = "%c{1}:%L %d{yyyy-MM-dd HH:mm:ss} %-5p - %m%n";
        consoleAppender.setLayout(new PatternLayout(pattern));
        consoleAppender.setThreshold(Level.ERROR);
        consoleAppender.activateOptions();
        Logger.getRootLogger().addAppender(consoleAppender);
    }


    @Override
    public void onFatal(Logger logger, Exception e) {
        logger.fatal(e.getMessage());
    }

    @Override
    public void onError(Logger logger, Exception e) {
        logger.error(e.getMessage());
    }

    @Override
    public void onWarn(Logger logger, Exception e) {
        logger.warn(e.getMessage());
    }

    @Override
    public void onInfo(Logger logger, String info) {
        logger.info(info);
    }
}
