package fr.tse.fise2.heapoverflow.database;

public class FavoriteRow {
    private int uid;
    private int id;
    private int type;
    private String name;
    private int userId;
    private boolean favorite;

    public FavoriteRow(int id, int userId, int type,String name) {
        this(-1, id, type, name, userId, false);
    }

    public FavoriteRow(int uid, int id, int type, String name, int userId, boolean favorite) {
        this.uid = uid;
        this.id = id;
        this.type = type;
        this.name = name;
        this.userId = userId;
        this.favorite = favorite;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
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
}
