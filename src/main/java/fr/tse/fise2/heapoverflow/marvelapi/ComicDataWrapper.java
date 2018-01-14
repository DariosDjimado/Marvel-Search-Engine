package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * ComicDataWrapper POJO
 *
 * @author Darios DJIMADO
 */
public class ComicDataWrapper extends TemplateDataWrapper {

    private ComicDataContainer data;

    public ComicDataContainer getData() {
        return data;
    }

    public void setData(ComicDataContainer data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ComicDataWrapper{" +
                "data=" + data +
                ", attributionText='" + attributionText + '\'' +
                ", etag='" + etag + '\'' +
                ", status='" + status + '\'' +
                ", copyright='" + copyright + '\'' +
                ", code='" + code + '\'' +
                ", attributionHTML='" + attributionHTML + '\'' +
                '}';
    }
}
