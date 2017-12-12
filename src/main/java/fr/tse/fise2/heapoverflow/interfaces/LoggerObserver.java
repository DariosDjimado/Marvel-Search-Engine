package fr.tse.fise2.heapoverflow.interfaces;

import org.apache.log4j.Logger;

public interface LoggerObserver {
    @Deprecated
    void onError(Logger logger, Exception e);

    @Deprecated
    void onInfo(Logger logger, String info);

}
