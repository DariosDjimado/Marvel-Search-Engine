package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class ComicDataContainer extends TemplateDataContainer {

    private Comic[] results;

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
