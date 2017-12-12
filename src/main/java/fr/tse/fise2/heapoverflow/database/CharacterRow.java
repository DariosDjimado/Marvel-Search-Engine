package fr.tse.fise2.heapoverflow.database;

public class CharacterRow {
    private int id;
    private String name;
    private String wikipediaEnUrl;
    private String wikipediaFrUrl;


    public CharacterRow(int id, String name) {
        this(id, name, "", "");
    }

    public CharacterRow(int id, String name, String wikipediaEnUrl, String wikipediaFrUrl) {
        this.id = id;
        this.name = name;
        this.wikipediaEnUrl = wikipediaEnUrl;
        this.wikipediaFrUrl = wikipediaFrUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWikipediaEnUrl() {
        return wikipediaEnUrl;
    }

    public void setWikipediaEnUrl(String wikipediaEnUrl) {
        this.wikipediaEnUrl = wikipediaEnUrl;
    }

    public String getWikipediaFrUrl() {
        return wikipediaFrUrl;
    }

    public void setWikipediaFrUrl(String wikipediaFrUrl) {
        this.wikipediaFrUrl = wikipediaFrUrl;
    }

    @Override
    public String toString() {
        return "CharacterRow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", wikipediaEnUrl='" + wikipediaEnUrl + '\'' +
                ", wikipediaFrUrl='" + wikipediaFrUrl + '\'' +
                '}';
    }
}
