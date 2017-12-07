package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.*;

import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.*;

public class InfoSubRequestsThread implements Runnable {
    DataShow caller;
    Thread th;
    Queue<Job> jobs;

    public InfoSubRequestsThread(DataShow caller) {
        this.caller = caller;
        this.jobs = new ConcurrentLinkedQueue<>();
        this.th = new Thread(this);
        th.start();
    }

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

                        caller.updateList(fetched, thisJob.elementType, thisJob.modelKey, thisJob.elementHash);
                        System.out.println("----" + thisJob + " Done");
                    }
                    while (offset + count < total);
                } catch (SocketTimeoutException e){
                    caller.updateList(fetched, "TimeOut", thisJob.modelKey, thisJob.elementHash);
                    System.err.println("----" + thisJob + " Timed out");
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            } catch(InterruptedException e){
                break;
            }
        }
    }

    public void addJob(String elementsClass, String modelKey, String shortUri, int elementHash){
        synchronized (this) {
            jobs.add(new Job(elementsClass, modelKey, shortUri, elementHash));
            System.out.println("----New Job for" + elementsClass);
            notify();
        }
    }
}

class Job {
    String elementType;
    String modelKey;
    String shortUri;
    int elementHash;


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