package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class EventList extends TemplateList {

    private EventSummary[] items;

    public EventSummary[] getItems() {
        return items;
    }

    public void setItems(EventSummary[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "EventList{" +
                "items=" + Arrays.toString(items) +
                ", collectionURI='" + collectionURI + '\'' +
                ", available=" + available +
                ", returned=" + returned +
                '}';
    }
}
