package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

/**
 * SeriesDataContainer POJO
 *
 * @author Darios DJIMADO
 */
public class SeriesDataContainer extends TemplateDataContainer {
    private Serie[] results;

    public Serie[] getResults() {
        return results;
    }

    public void setResults(Serie[] results) {
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
