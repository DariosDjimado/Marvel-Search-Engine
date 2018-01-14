package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * This class consists exclusively of public methods that return or set protected fields. The fields are common of all
 * class class *.List. This POJO doesn't contain the override toString method.
 *
 * @author Darios DJIMADO
 */
public class TemplateList {

    String collectionURI;

    int available;

    int returned;

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getReturned() {
        return returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }
}
