package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import fr.tse.fise2.heapoverflow.marvelapi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.*;

/**
 * Object to manage subrequest thread for datashow panel
 */
class InfoSubRequestsThread implements Runnable {

    private static InfoSubRequestsThread isrt = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(InfoSubRequestsThread.class);
    /**
     * The thread executing the queries
     */
    private Thread th;
    /**
     * A FIFO stack of jobs to do
     */
    private volatile Queue<Job> jobs;
    /**
     * last canceled token to interrupt job in progress
     */
    private int lastCanceledToken;

    /**
     * Constructor, initialize job stack and start thread
     */
    private InfoSubRequestsThread() {
        this.jobs = new ConcurrentLinkedQueue<>();
        this.th = new Thread(this);
        th.start();
    }

    public static InfoSubRequestsThread getInstance(){
        if(isrt == null){
            isrt = new InfoSubRequestsThread();
        }
        return isrt;
    }

    /**
     * The program of the thread
     */
    @Override
    public void run() {
        Job thisJob;
        MarvelRequest request = MarvelRequest.getInstance();
        Set fetched;
        boolean cancelling;
        while (true) {
            try {
                cancelling = false;
                synchronized (this) {
                    while (jobs.isEmpty()) {
                        wait();
                    }
                    thisJob = jobs.poll();
                }

                switch (thisJob.elementType) {
                    case "Comic":
                        fetched = new TreeSet<Comic>();
                        break;
                    case "Character":
                        fetched = new TreeSet<Comic>();
                        break;
                    case "Creator":
                        fetched = new TreeSet<Creator>();
                        break;
                    case "Story":
                        fetched = new TreeSet<Story>();
                        break;
                    case "Event":
                        fetched = new TreeSet<Event>();
                        break;
                    case "Serie":
                        fetched = new TreeSet<Serie>();
                        break;
                    default:
                        if (LOGGER.isErrorEnabled()) {
                            LOGGER.error("Unknown type " + thisJob.elementType);
                        }
                        continue;
                }

                try {
                    int reqCount = 0;
                    int offset = 0;
                    int count = 0;
                    int total = 0;
                    do {
                        if (thisJob.elementHash == lastCanceledToken) {
                            cancelling = true;
                            break;
                        }
                        String response = request.getData(thisJob.shortUri, "limit=100&offset=" + 100 * reqCount);
                        switch (thisJob.elementType) {
                            case "Comic":
                                ComicDataWrapper responseComic = deserializeComics(response);
                                fetched.addAll(Arrays.asList(responseComic.getData().getResults()));
                                offset = responseComic.getData().getOffset();
                                count = responseComic.getData().getCount();
                                total = responseComic.getData().getTotal();
                                break;
                            case "Character":
                                CharacterDataWrapper responseCharacter = deserializeCharacters(response);
                                fetched.addAll(Arrays.asList(responseCharacter.getData().getResults()));
                                offset = responseCharacter.getData().getOffset();
                                count = responseCharacter.getData().getCount();
                                total = responseCharacter.getData().getTotal();
                                break;
                            case "Creator":
                                CreatorDataWrapper responseCreator = deserializeCreators(response);
                                fetched.addAll(Arrays.asList(responseCreator.getData().getResults()));
                                offset = responseCreator.getData().getOffset();
                                count = responseCreator.getData().getCount();
                                total = responseCreator.getData().getTotal();
                                break;
                            case "Story":
                                StoriesDataWrapper responseStory = deserializeStories(response);
                                fetched.addAll(Arrays.asList(responseStory.getData().getResults()));
                                offset = responseStory.getData().getOffset();
                                count = responseStory.getData().getCount();
                                total = responseStory.getData().getTotal();
                                break;
                            case "Event":
                                EventsDataWrapper responseEvents = deserializeEvents(response);
                                fetched.addAll(Arrays.asList(responseEvents.getData().getResults()));
                                offset = responseEvents.getData().getOffset();
                                count = responseEvents.getData().getCount();
                                total = responseEvents.getData().getTotal();
                                break;
                            case "Serie":
                                SeriesDataWrapper responseStories = deserializeSeries(response);
                                fetched.addAll(Arrays.asList(responseStories.getData().getResults()));
                                offset = responseStories.getData().getOffset();
                                count = responseStories.getData().getCount();
                                total = responseStories.getData().getTotal();
                                break;
                        }
                        reqCount++;
                        if(offset + count < total) { //not the last request
                            thisJob.getCaller().updateList(fetched, thisJob.elementType, thisJob.modelKey, thisJob.elementHash, false, new LoadingListElement());
                        }
                        else {
                            thisJob.getCaller().updateList(fetched, thisJob.elementType, thisJob.modelKey, thisJob.elementHash, true, null);
                        }
                    }
                    while (offset + count < total);
                    if (cancelling) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("----" + thisJob + " Cancelled");
                        }
                        continue;
                    }
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("----" + thisJob + " Done");
                    }

                } catch (SocketTimeoutException e) {
                    thisJob.getCaller().updateList(fetched, thisJob.elementType, thisJob.modelKey, thisJob.elementHash, true, new TimeoutListElement());
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("----" + thisJob + " Timed out");
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
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * Method to add a job to the stack
     *
     * @param elementsClass a string representing the class of elements to get
     * @param modelKey      the key of the model to fill (key of {@link DataShow#tabsJLists})
     * @param shortUri      the short URI to request the set of datas
     * @param elementHash   a hash of the element displayed to avoid editing à list with an old request
     */
    public void addJob(String elementsClass, String modelKey, String shortUri, int elementHash, SubRequestCaller caller) {
        synchronized (this) {
            jobs.add(new Job(elementsClass, modelKey, shortUri, elementHash, caller));
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("----New Job for" + elementsClass);
            }
            notify();
        }
    }

    public synchronized void clearJobsFor(int elementHash) {
        lastCanceledToken = elementHash;
        Queue<Job> clearedQueue = new ConcurrentLinkedQueue<>();
        while (!jobs.isEmpty()) {
            Job job = jobs.poll();
            if (job.elementHash != elementHash) {
                clearedQueue.add(job);
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("----" + job + " Canceled");
                }
            }
        }
        jobs = clearedQueue;
    }
}

/**
 * A class representing a job
 */
class Job {
    private final SubRequestCaller caller;
    String elementType;
    String modelKey;
    String shortUri;
    int elementHash;


    /**
     * Constructor
     *
     * @param elementType a string representing the class of elements to get
     * @param modelKey    the key of the model to fill (key of {@link DataShow#tabsJLists})
     * @param shortUri    the short URI to request the set of datas
     * @param elementHash a hash of the element displayed to avoid editing à list with an old request
     */
    public Job(String elementType, String modelKey, String shortUri, int elementHash, SubRequestCaller caller) {
        this.elementType = elementType;
        this.modelKey = modelKey;
        this.shortUri = shortUri;
        this.elementHash = elementHash;
        this.caller = caller;
    }

    @Override
    public String toString() {
        return "Job{" +
                "elementType='" + elementType + '\'' +
                ", modelKey='" + modelKey + '\'' +
                ", shortUri='" + shortUri + '\'' +
                ", elementHash=" + elementHash +
                '}';
    }

    public SubRequestCaller getCaller() {
        return caller;
    }
}