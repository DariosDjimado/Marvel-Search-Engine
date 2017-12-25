package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.marvelapi.MarvelElement;

public final class MarvelElementsRow {
    private int uid;
    private int id;
    private MarvelElement type;

    public MarvelElementsRow(int uid, int id, MarvelElement type) {
        this.uid = uid;
        this.id = id;
        this.type = type;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MarvelElement getType() {
        return type;
    }

    public void setType(MarvelElement type) {
        this.type = type;
    }
}
