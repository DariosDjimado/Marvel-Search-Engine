package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import fr.tse.fise2.heapoverflow.main.Controller;
import fr.tse.fise2.heapoverflow.marvelapi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.Line;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.*;

/**
 * Object to manage subrequest thread for datashow panel
 */
public class InfoSubRequestsThread implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfoSubRequestsThread.class);
    /**
     * the datashow element to callback
     */
    DataShow caller;
    /**
     * The thread executing the queries
     */
    Thread th;
    /**
     * A FIFO stack of jobs to do
     */
    Queue<Job> jobs;

    /**
     * Constructor, initialize job stack and start thread
     * @param caller
     */
    public InfoSubRequestsThread(DataShow caller) {
        this.caller = caller;
        this.jobs = new ConcurrentLinkedQueue<>();
        this.th = new Thread(this);
        th.start();
    }

    /**
     * The program of the thread
     */
    @Override
    public void run() {
        Job thisJob;
        MarvelRequest request = new MarvelRequest();
        Set fetched;
        while (true) {
            try {
                synchronized (this) {
                    while (jobs.isEmpty()) {
                        wait();
                    }
                    thisJob = jobs.poll();
                }

                switch (thisJob.elementType){
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
                        System.err.println("Unknown type " + thisJob.elementType);
                        continue;
                }

                try {
                    int reqCount = 0;
                    int offset = 0;
                    int count = 0;
                    int total = 0;
                    do {
                        String response = request.getData(thisJob.shortUri + "?limit=100&offset=" + 100 * reqCount);
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

                    }
                    while (offset + count < total);
                    caller.updateList(fetched, thisJob.elementType, thisJob.modelKey, thisJob.elementHash);
                    System.out.println("----" + thisJob + " Done");

                } catch (SocketTimeoutException e){
                    caller.updateList(fetched, "TimeOut", thisJob.modelKey, thisJob.elementHash);
                    System.err.println("----" + thisJob + " Timed out");
                    AppErrorHandler.onError(e);
                    if(LOGGER.isErrorEnabled()){
                        LOGGER.error(e.getMessage(),e);
                    }

                } catch (Exception e) {
                    AppErrorHandler.onError(e);
                    if(LOGGER.isErrorEnabled()){
                        LOGGER.error(e.getMessage(),e);
                    }
                }
            } catch(InterruptedException e){
                break;
            }
        }
    }

    /**
     * Method to add a job to the stack
     * @param elementsClass a string representing the class of elements to get
     * @param modelKey the key of the model to fill (key of {@link DataShow#tabsJLists})
     * @param shortUri the short URI to request the set of datas
     * @param elementHash a hash of the element displayed to avoid editing à list with an old request
     */
    public void addJob(String elementsClass, String modelKey, String shortUri, int elementHash){
        synchronized (this) {
            jobs.add(new Job(elementsClass, modelKey, shortUri, elementHash));
            System.out.println("----New Job for" + elementsClass);
            notify();
        }
    }
}

/**
 * A class representing a job
 */
class Job {
    String elementType;
    String modelKey;
    String shortUri;
    int elementHash;


    /**
     * Constructor
     * @param elementType a string representing the class of elements to get
     * @param modelKey the key of the model to fill (key of {@link DataShow#tabsJLists})
     * @param shortUri the short URI to request the set of datas
     * @param elementHash a hash of the element displayed to avoid editing à list with an old request
     */
    public Job(String elementType, String modelKey, String shortUri, int elementHash) {
        this.elementType = elementType;
        this.modelKey = modelKey;
        this.shortUri = shortUri;
        this.elementHash = elementHash;
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
}