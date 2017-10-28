package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class ComicList {
    private ComicSummary[] items;

    private String collectionURI;

    private String available;

    private String returned;

    public ComicSummary[] getItems() {
        return items;
    }

    public void setItems(ComicSummary[] items) {
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
        return "ComicList [items = " + Arrays.toString(items) + ", collectionURI = " + collectionURI + ", available = "
                + available + ", returned = " + returned + "]";
    }
}
