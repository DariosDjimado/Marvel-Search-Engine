package fr.tse.fise2.heapoverflow.database;

public class FavoriteRow {
    private int id;
    private int type;
    private int userId;

    public FavoriteRow(int id, int type, int userId) {
        this.id = id;
        this.type = type;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
