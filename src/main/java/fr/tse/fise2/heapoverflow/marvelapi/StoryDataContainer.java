package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

/**
 * StoryDataContainer POJO
 *
 * @author Darios DJIMADO
 */
public class StoryDataContainer extends TemplateDataContainer {

    private Story[] results;

    public Story[] getResults() {
        return results;
    }

    public StoryDataContainer setResults(Story[] results) {
        this.results = results;
        return this;
    }

    @Override
    public String toString() {
        return "StoryDataContainer{" +
                "results=" + Arrays.toString(results) +
                ", total=" + total +
                ", limit=" + limit +
                ", count=" + count +
                ", offset=" + offset +
                '}';
    }
}
