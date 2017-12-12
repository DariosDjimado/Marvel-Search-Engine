package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.marvelapi.MarvelElements;

public class MarvelElementsRow {
    private int uid;
    private int id;
    private MarvelElements type;

    public MarvelElementsRow(int uid, int id, MarvelElements type) {
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

    public MarvelElements getType() {
        return type;
    }

    public void setType(MarvelElements type) {
        this.type = type;
    }
}
