package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Comic;

import java.util.Objects;

public class ComicListElement implements MarvelListElement {
    private Comic comic;

    public ComicListElement(Comic comic) {
        this.comic = comic;
    }

    @Override
    public Object getDispedO() {
        return comic;
    }

    @Override
    public String toString() {
        return comic.getTitle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComicListElement that = (ComicListElement) o;
        return Objects.equals(comic, that.comic);
    }

    @Override
    public int hashCode() {

        return Objects.hash(comic);
    }
}
