package fr.tse.fise2.heapoverflow.wikidata;

class WdEntities {
    public WdEntities(WdCustomEntity wdCustomEntity) {
        this.customEntity = wdCustomEntity;
    }

    private WdCustomEntity customEntity;


    public WdCustomEntity getCustomEntity() {
        return customEntity;
    }

    public void setCustomEntity(WdCustomEntity wdCustomEntity) {
        this.customEntity = wdCustomEntity;
    }

    @Override
    public String toString() {
        return "WdEntities{" +
                "customEntity=" + customEntity +
                '}';
    }
}
