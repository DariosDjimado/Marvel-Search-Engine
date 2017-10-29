package fr.tse.fise2.heapoverflow.marvelapi;

public class StorySummary extends TemplateSummary {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StorySummary{" +
                "type='" + type + '\'' +
                ", resourceURI='" + resourceURI + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
