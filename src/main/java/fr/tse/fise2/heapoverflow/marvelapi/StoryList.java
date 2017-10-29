package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class StoryList extends TemplateList {

    private StorySummary[] items;

    public StorySummary[] getItems() {
        return items;
    }

    public void setItems(StorySummary[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "StoryList{" +
                "items=" + Arrays.toString(items) +
                ", collectionURI='" + collectionURI + '\'' +
                ", available=" + available +
                ", returned=" + returned +
                '}';
    }
}
