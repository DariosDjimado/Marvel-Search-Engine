package fr.tse.fise2.heapoverflow.wikidata;

public class WdMatch {
    private String text;

    private String language;

    private String type;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "WdMatch{" +
                "text='" + text + '\'' +
                ", language='" + language + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
