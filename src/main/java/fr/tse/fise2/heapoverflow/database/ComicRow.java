package fr.tse.fise2.heapoverflow.database;

public class ComicRow {
    private int id;
    private String title;

    public ComicRow(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "ComicRow{" +
                "id=" + id +
                ", title='" + title +
                '}';
    }
}
