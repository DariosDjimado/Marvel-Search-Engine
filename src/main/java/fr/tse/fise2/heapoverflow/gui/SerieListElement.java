package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Serie;

import java.util.Objects;

public class SerieListElement implements MarvelListElement {

    private Serie serie;

    public SerieListElement(Serie serie) {
        this.serie = serie;
    }

    @Override
    public Object getDispedO() {
        return serie;
    }

    @Override
    public String toString() {
        return serie.getTitle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerieListElement that = (SerieListElement) o;
        return Objects.equals(serie, that.serie);
    }

    @Override
    public int hashCode() {

        return Objects.hash(serie);
    }
}
