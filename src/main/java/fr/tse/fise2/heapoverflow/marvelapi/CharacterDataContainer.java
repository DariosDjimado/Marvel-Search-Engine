package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class CharacterDataContainer extends TemplateDataContainer {

    private Character[] results;

    public Character[] getResults() {
        return results;
    }

    public void setResults(Character[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "CharacterDataContainer{" +
                "results=" + Arrays.toString(results) +
                ", total=" + total +
                ", limit=" + limit +
                ", count=" + count +
                ", offset=" + offset +
                '}';
    }
}
