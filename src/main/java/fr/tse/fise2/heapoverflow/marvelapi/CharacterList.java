package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class CharacterList extends TemplateList {

    private CharacterSummary[] items;

    public CharacterSummary[] getItems() {
        return items;
    }

    public void setItems(CharacterSummary[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CharacterList{" +
                "items=" + Arrays.toString(items) +
                ", collectionURI='" + collectionURI + '\'' +
                ", available=" + available +
                ", returned=" + returned +
                '}';
    }
}
