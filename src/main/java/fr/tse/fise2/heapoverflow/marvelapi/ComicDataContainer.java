package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class ComicDataContainer extends TemplateDataContainer {

    private Comic[] results;

    public Comic[] getResults() {
        return results;
    }

    public void setResults(Comic[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ComicDataContainer{" +
                "results=" + Arrays.toString(results) +
                ", total=" + total +
                ", limit=" + limit +
                ", count=" + count +
                ", offset=" + offset +
                '}';
    }
}
