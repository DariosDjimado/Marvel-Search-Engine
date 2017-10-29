package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class ComicList extends TemplateList {

    private ComicSummary[] items;

    public ComicSummary[] getItems() {
        return items;
    }

    public void setItems(ComicSummary[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ComicList{" +
                "items=" + Arrays.toString(items) +
                ", collectionURI='" + collectionURI + '\'' +
                ", available=" + available +
                ", returned=" + returned +
                '}';
    }
}
