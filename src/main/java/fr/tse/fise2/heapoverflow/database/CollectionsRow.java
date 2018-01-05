package fr.tse.fise2.heapoverflow.database;

public class CollectionsRow {
    private int collectionId;
    private String title;
    private String description;
    private int usersId;

    public CollectionsRow(int collectionId, String title, String description, int usersId) {
        this.collectionId = collectionId;
        this.title = title;
        this.description = description;
        this.usersId = usersId;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUsersId() {
        return usersId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    @Override
    public String toString() {
        return "CollectionsRow{" +
                "collectionId=" + collectionId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", usersId=" + usersId +
                '}';
    }
}
