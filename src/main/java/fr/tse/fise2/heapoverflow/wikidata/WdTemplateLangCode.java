package fr.tse.fise2.heapoverflow.wikidata;

public class WdTemplateLangCode {

    private String value;

    private String language;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    @Override
    public String toString() {
        return "WdWdTemplateLangCode{" +
                "value='" + value + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
