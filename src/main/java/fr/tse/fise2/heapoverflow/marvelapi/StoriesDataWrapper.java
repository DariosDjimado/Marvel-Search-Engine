package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * StoryDataWrapper POJO
 *
 * @author Darios DJIMADO
 */
public class StoriesDataWrapper extends TemplateDataWrapper {

    private StoryDataContainer data;

    public StoryDataContainer getData() {
        return data;
    }

    public StoriesDataWrapper setData(StoryDataContainer data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "StoriesDataWrapper{" +
                "data=" + data +
                ", attributionText='" + attributionText + '\'' +
                ", etag='" + etag + '\'' +
                ", status='" + status + '\'' +
                ", copyright='" + copyright + '\'' +
                ", code=" + code +
                ", attributionHTML='" + attributionHTML + '\'' +
                '}';
    }
}
