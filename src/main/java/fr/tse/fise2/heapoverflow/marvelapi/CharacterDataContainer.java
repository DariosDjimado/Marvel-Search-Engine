package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class CharacterDataContainer {
    private String total;

    private String limit;

    private Character[] results;

    private String count;

    private String offset;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public Character[] getResults() {
        return results;
    }

    public void setResults(Character[] results) {
        this.results = results;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "CharacterDataContainer [total = " + total + ", limit = " + limit + ", results = " +
                Arrays.toString(results) + ", count = " + count + ", offset = " + offset + "]";
    }
}
