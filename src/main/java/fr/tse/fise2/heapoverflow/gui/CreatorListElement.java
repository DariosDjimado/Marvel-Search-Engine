package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Creator;

import java.util.Objects;

public class CreatorListElement implements MarvelListElement {

    private Creator creator;

    public CreatorListElement(Creator creator) {
        this.creator = creator;
    }

    @Override
    public Object getDispedO() {
        return creator;
    }

    @Override
    public String toString() {
        return creator.getFullName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatorListElement that = (CreatorListElement) o;
        return Objects.equals(creator, that.creator);
    }

    @Override
    public int hashCode() {

        return Objects.hash(creator);
    }
}
