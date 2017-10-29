package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * This class consists exclusively of public methods that return or set protected fields. The fields are common of all
 * class class *.List. This POJO doesn't contain the override toString method.
 */
public class TemplateList {

    protected String collectionURI;

    protected String available;

    protected String returned;

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getReturned() {
        return returned;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }
}
