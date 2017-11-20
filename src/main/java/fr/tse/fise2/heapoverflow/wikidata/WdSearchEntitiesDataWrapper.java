package fr.tse.fise2.heapoverflow.wikidata;

import java.util.Arrays;

public class WdSearchEntitiesDataWrapper {
    private WdSearchEntity[] search;

    private WdSearchInfo searchinfo;

    private String searchContinue;

    private String success;

    public WdSearchEntity[] getSearch() {
        return search;
    }

    public void setSearch(WdSearchEntity[] search) {
        this.search = search;
    }

    public WdSearchInfo getSearchinfo() {
        return searchinfo;
    }

    public void setSearchinfo(WdSearchInfo searchinfo) {
        this.searchinfo = searchinfo;
    }

    public String getSearchContinue() {
        return searchContinue;
    }

    public void setSearchContinue(String searchContinue) {
        this.searchContinue = searchContinue;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "WdSearchEntitiesDataWrapper{" +
                "search=" + Arrays.toString(search) +
                ", searchinfo=" + searchinfo +
                ", searchContinue='" + searchContinue + '\'' +
                ", success='" + success + '\'' +
                '}';
    }
}
