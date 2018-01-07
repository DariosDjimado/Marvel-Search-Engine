package fr.tse.fise2.heapoverflow.database;

public class FirstAppearanceRow {
    String character;
    String date;
    String comic;

    public FirstAppearanceRow(String character, String date, String comic) {
        this.character = character;
        this.date = date;
        this.comic = comic;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComic() {
        return comic;
    }

    public void setComic(String comic) {
        this.comic = comic;
    }

    @Override
    public String toString() {
        return "FirstAppearanceRow{" +
                "character='" + character + '\'' +
                ", date='" + date + '\'' +
                ", comic='" + comic + '\'' +
                '}';
    }
}
