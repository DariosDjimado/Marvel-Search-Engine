package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

/**
 * Created by théo on 22/11/2017.
 */
public class CreatorDataContainer extends TemplateDataContainer{
    private Creator[] results;

    public Creator[] getResults() {
        return results;
    }

    public void setResults(Creator[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "CreatorDataContainer{" +
                "results=" + Arrays.toString(results) +
                ", total=" + total +
                ", limit=" + limit +
                ", count=" + count +
                ", offset=" + offset +
                '}';
    }
}
