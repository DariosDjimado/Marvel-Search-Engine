package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class EventList {
    private EventSummary[] items;

    private String collectionURI;

    private String available;

    private String returned;

    public EventSummary[] getItems() {
        return items;
    }

    public void setItems(EventSummary[] items) {
        this.items = items;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getReturned() {
        return returned;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        return "EventList [items = " + Arrays.toString(items) + ", collectionURI = " + collectionURI + ", available = "
                + available + ", returned = " + returned + "]";
    }
}
