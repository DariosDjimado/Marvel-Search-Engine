package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * This class consists exclusively of public methods that return or set protected fields. The fields are common of all
 * class class *.DataContainer. This POJO doesn't contain the override toString method.
 */
public class TemplateDataContainer {
    protected String total;

    protected String limit;

    protected String count;

    protected String offset;

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
}
