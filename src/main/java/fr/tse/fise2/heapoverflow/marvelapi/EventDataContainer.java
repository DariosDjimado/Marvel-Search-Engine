package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class EventDataContainer extends TemplateDataContainer {
    private Event[] results;

    public Event[] getResults() {
        return results;
    }

    public EventDataContainer setResults(Event[] results) {
        this.results = results;
        return this;
    }

    @Override
    public String toString() {
        return "EventDataContainer{" +
                "results=" + Arrays.toString(results) +
                ", total=" + total +
                ", limit=" + limit +
                ", count=" + count +
                ", offset=" + offset +
                '}';
    }
}
