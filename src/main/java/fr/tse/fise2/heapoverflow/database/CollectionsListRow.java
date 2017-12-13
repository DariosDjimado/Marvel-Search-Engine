package fr.tse.fise2.heapoverflow.database;

public class CollectionsListRow {
    private int collection_id;
    private String title;
    private String description;

    public CollectionsListRow(int collection_id, String title) {
        this.collection_id = collection_id;
        this.title = title;
    }

    public CollectionsListRow(int collection_id, String title, String description) {
        this.collection_id = collection_id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return collection_id;
    }

    public void setId(int collection_id) {
        this.collection_id = collection_id;
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

    @Override
    public String toString() {
        return "CollectionsListRow{" +
                "collection_id=" + collection_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
