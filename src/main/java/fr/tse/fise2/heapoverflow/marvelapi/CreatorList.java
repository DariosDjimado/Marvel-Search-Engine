package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

/**
 * CreatorList POJO
 *
 * @author Darios DJIMADO
 */
public class CreatorList extends TemplateList {

    private CreatorSummary[] items;

    public CreatorSummary[] getItems() {
        return items;
    }

    public void setItems(CreatorSummary[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CreatorList{" +
                "items=" + Arrays.toString(items) +
                ", collectionURI='" + collectionURI + '\'' +
                ", available='" + available + '\'' +
                ", returned='" + returned + '\'' +
                '}';
    }
}
