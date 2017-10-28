package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class StoryList {
    private StorySummary[] items;

    private String collectionURI;

    private String available;

    private String returned;

    public StorySummary[] getItems() {
        return items;
    }

    public void setItems(StorySummary[] items) {
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
        return "StoryList [items = " + Arrays.toString(items) + ", collectionURI = " + collectionURI + ", available = "
                + available + ", returned = " + returned + "]";
    }
}
