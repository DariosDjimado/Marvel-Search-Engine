package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * This class consists exclusively of public methods that return or set protected fields. The fields are common of all
 * class class *.DataContainer. This POJO doesn't contain the override toString method.
 *
 * @author Darios DJIMADO
 */
public class TemplateDataContainer {
    int total;

    int limit;

    int count;

    int offset;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
