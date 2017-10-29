package fr.tse.fise2.heapoverflow.marvelapi;

public class TextObject {

    private String type;

    private String language;

    private String text;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TextObject{" +
                "type='" + type + '\'' +
                ", language='" + language + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
    
}
