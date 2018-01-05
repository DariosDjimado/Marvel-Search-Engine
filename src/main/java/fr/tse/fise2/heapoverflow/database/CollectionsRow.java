package fr.tse.fise2.heapoverflow.database;

public class CollectionsRow {
    private int collectionId;
    private String title;
    private String description;
    private int userId;

    public CollectionsRow(int collectionId, String title, String description, int userId) {
        this.collectionId = collectionId;
        this.title = title;
        this.description = description;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CollectionsRow{" +
                "collection_id=" + collectionId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", user_id=" + userId +
                '}';
    }
}
