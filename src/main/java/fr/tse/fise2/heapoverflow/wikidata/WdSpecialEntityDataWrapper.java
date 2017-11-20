package fr.tse.fise2.heapoverflow.wikidata;

public class WdSpecialEntityDataWrapper {

    private WdEntities entities;

    public WdSpecialEntityDataWrapper(WdEntities wdEntities) {
        this.entities = wdEntities;
    }

    public WdEntities getEntities() {
        return entities;
    }

    public void setEntities(WdEntities wdEntities) {
        this.entities = wdEntities;
    }

    @Override
    public String toString() {
        return "WdSpecialEntityDataWrapper{" +
                "entities=" + entities +
                '}';
    }
}
