package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;
import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.ComicDataWrapper;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;
import fr.tse.fise2.heapoverflow.marvelapi.Serie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

/**
 * Object to manage subrequest thread for datashow panel
 */
public class ComputeNextToReadThread implements Runnable {

    private static ComputeNextToReadThread isrt = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(InfoSubRequestsThread.class);
    /**
     * The thread executing the queries
     */
    private Thread th;

    private List<ElementAssociationRow> readComics = null;

    private DefaultListModel<Comic> targetModel = null;

    private boolean jobDone = true;

    /**
     * Constructor, initialize job stack and start thread
     */
    private ComputeNextToReadThread() {
        this.th = new Thread(this);
        th.start();
    }

    public static ComputeNextToReadThread getInstance() {
        if (isrt == null) {
            isrt = new ComputeNextToReadThread();
        }
        return isrt;
    }

    /**
     * The program of the thread
     */
    @Override
    public void run() {
        MarvelRequest request = MarvelRequest.getInstance();
        while (true) {
            try {
                synchronized (this) {
                    wait();
                }
                if (jobDone) {
                    continue;
                }

                while (!readComics.isEmpty()) {
                    ElementAssociationRow ear = readComics.get(0);
                    ArrayList<Comic> fetched = new ArrayList<>();
                    Serie serieRead;
                    try {
                        String comicResponse = request.getData("comics/" + ear.getElementID(), null);
                        Comic comicRead = MarvelRequest.deserializeComics(comicResponse).getData().getResults()[0];
                        String serieResponse = request.getData(comicRead.getSeries().getResourceURI().substring(36), null);
                        serieRead = MarvelRequest.deserializeSeries(serieResponse).getData().getResults()[0];
                    } catch (IOException e) {
                        AppErrorHandler.onError(e);
                        if (LOGGER.isErrorEnabled()) {
                            LOGGER.error(e.getMessage(), e);
                        }
                        continue;
                    }
                    try {
                        int reqCount = 0;
                        int offset = 0;
                        int count = 0;
                        int total = 0;
                        do {
                            String serieResponse = request.getData(serieRead.getComics().getCollectionURI().substring(36), "limit=100&offset=" + 100 * reqCount);
                            ComicDataWrapper responseComic = deserializeComics(serieResponse);
                            fetched.addAll(Arrays.asList(responseComic.getData().getResults()));
                            offset = responseComic.getData().getOffset();
                            count = responseComic.getData().getCount();
                            total = responseComic.getData().getTotal();
                            reqCount++;
                        }
                        while (offset + count < total);
                        fetched.sort(new Comparator<Comic>() {
                            @Override
                            public int compare(Comic o1, Comic o2) {
                                return o1.getIssueNumber().compareTo(o2.getIssueNumber());
                            }
                        });

                        boolean nextInSerieDisplayed = false;
                        for (Comic comicInSerie : fetched) {
                            int j = 0;
                            boolean read = false;
                            while (j < readComics.size()) {
                                ElementAssociationRow earRead = readComics.get(j);
                                if (comicInSerie.getId() == earRead.getElementID()) {
                                    readComics.remove(earRead);
                                    read = true;
                                    break;
                                } else {
                                    j++;
                                }
                            }
                            if (!read && !nextInSerieDisplayed) {
                                nextInSerieDisplayed = true;
                                targetModel.addElement(comicInSerie);
                            }
                        }

                    } catch (SocketTimeoutException e) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("----" + serieRead.getComics().getCollectionURI().substring(36) + " Timed out");
                        }
                        AppErrorHandler.onError(e);
                        if (LOGGER.isErrorEnabled()) {
                            LOGGER.error(e.getMessage(), e);
                        }

                    } catch (Exception e) {
                        AppErrorHandler.onError(e);
                        if (LOGGER.isErrorEnabled()) {
                            LOGGER.error(e.getMessage(), e);
                        }
                    }
                }

                jobDone = true;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("----Refresh Done");
            }

            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void refresh(List<ElementAssociationRow> readComics, DefaultListModel<Comic> modelToUpdate) {
        synchronized (this) {
            this.readComics = readComics;
            this.targetModel = modelToUpdate;
            this.jobDone = false;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("----Refresh nextToRead");
            }
            notify();
        }
    }
}