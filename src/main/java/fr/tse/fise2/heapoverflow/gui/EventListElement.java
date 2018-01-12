package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Event;

import java.util.Objects;

public class EventListElement implements MarvelListElement {

    private Event event;

    public EventListElement(Event event) {
        this.event = event;
    }

    @Override
    public Object getDispedO() {
        return null;
    }

    @Override
    public String toString() {
        return event.getTitle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventListElement that = (EventListElement) o;
        return Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {

        return Objects.hash(event);
    }
}
