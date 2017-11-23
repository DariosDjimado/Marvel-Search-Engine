package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class SeriesDataContainer extends TemplateDataContainer {
    private Series[] results;

    public Series[] getResults() {
        return results;
    }

    public void setResults(Series[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "SeriesDataContainer{" +
                "results=" + Arrays.toString(results) +
                ", total=" + total +
                ", limit=" + limit +
                ", count=" + count +
                ", offset=" + offset +
                '}';
    }
}
