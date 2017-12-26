package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.marvelapi.MarvelElement;

public class ElementAssociationRow {
    private int uid;
    private int elementID;
    private MarvelElement type;
    private String name;
    private int userId;
    private boolean favorite;
    private int collectionID;
    private boolean read;
    private int grade;
    private String comment;


    public ElementAssociationRow(int uid, int elementID, MarvelElement type, String name, int userId, boolean favorite, int collectionID, boolean read, int grade, String comment) {
        this.uid = uid;
        this.elementID = elementID;
        this.type = type;
        this.name = name;
        this.userId = userId;
        this.favorite = favorite;
        this.collectionID = collectionID;
        this.read = read;
        this.grade = grade;
        this.comment = comment;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getElementID() {
        return elementID;
    }

    public void setElementID(int elementID) {
        this.elementID = elementID;
    }

    public MarvelElement getType() {
        return type;
    }

    public void setType(MarvelElement type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(int collectionID) {
        this.collectionID = collectionID;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ElementAssociationRow{" +
                "uid=" + uid +
                ", elementID=" + elementID +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", favorite=" + favorite +
                ", collectionID=" + collectionID +
                ", read=" + read +
                ", grade=" + grade +
                ", comment='" + comment + '\'' +
                '}';
    }
}
