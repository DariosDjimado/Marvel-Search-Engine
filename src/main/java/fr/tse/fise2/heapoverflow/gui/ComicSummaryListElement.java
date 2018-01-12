package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.ComicSummary;

import java.util.Objects;

public class ComicSummaryListElement implements MarvelListElement {

    private ComicSummary comicSummary;

    public ComicSummaryListElement(ComicSummary comicSummary) {
        this.comicSummary = comicSummary;
    }

    @Override
    public Object getDispedO() {
        return comicSummary;
    }

    @Override
    public String toString() {
        return comicSummary.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComicSummaryListElement that = (ComicSummaryListElement) o;
        return Objects.equals(comicSummary, that.comicSummary);
    }

    @Override
    public int hashCode() {

        return Objects.hash(comicSummary);
    }
}
