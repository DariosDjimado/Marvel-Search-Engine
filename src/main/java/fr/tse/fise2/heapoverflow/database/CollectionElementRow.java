package fr.tse.fise2.heapoverflow.database;

public class CollectionElementRow {
    private int element_id ;
    private int marvelElement_id;

    public CollectionElementRow(int element_id, int marvelElement_id) {
        this.element_id = element_id;
        this.marvelElement_id = marvelElement_id;
    }

    public int getElement_id() {
        return element_id;
    }

    public void setElement_id(int element_id) {
        this.element_id = element_id;
    }

    public int getMarvelElement_id() {
        return marvelElement_id;
    }

    public void setMarvelElement_id(int marvelElement_id) {
        this.marvelElement_id = marvelElement_id;
    }
}
