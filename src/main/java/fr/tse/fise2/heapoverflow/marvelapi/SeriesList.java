package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class SeriesList extends TemplateList {

    private SeriesSummary[] items;

    public SeriesSummary[] getItems() {
        return items;
    }

    public void setItems(SeriesSummary[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SeriesList{" +
                "items=" + Arrays.toString(items) +
                ", collectionURI='" + collectionURI + '\'' +
                ", available=" + available +
                ", returned=" + returned +
                '}';
    }
}
