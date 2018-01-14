package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * SeriesDataWrapper POJO
 *
 * @author Darios DJIMADO
 */
public class SeriesDataWrapper extends TemplateDataWrapper {

    private SeriesDataContainer data;

    public SeriesDataContainer getData() {
        return data;
    }

    public void setData(SeriesDataContainer data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SeriesDataWrapper{" +
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
