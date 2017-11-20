package fr.tse.fise2.heapoverflow.wikidata;

public class WdSearchInfo {

    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "WdSearchInfo{" +
                "search='" + search + '\'' +
                '}';
    }
}
