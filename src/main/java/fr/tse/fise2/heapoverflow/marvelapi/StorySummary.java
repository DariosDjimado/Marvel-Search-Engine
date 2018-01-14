package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * StorySummary POJO
 *
 * @author Darios DJIMADO
 */
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
