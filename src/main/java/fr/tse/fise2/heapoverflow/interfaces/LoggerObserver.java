package fr.tse.fise2.heapoverflow.interfaces;

import org.apache.log4j.Logger;

public interface LoggerObserver {

    void onFatal(Logger logger, Exception e);

    void onError(Logger logger, Exception e);

    void onWarn(Logger logger, Exception e);

    void onInfo(Logger logger, String info);

    void onDebug(Logger logger, String debug);

}
