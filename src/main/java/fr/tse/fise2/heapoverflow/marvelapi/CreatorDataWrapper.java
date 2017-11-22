package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * Created by théo on 22/11/2017.
 */
public class CreatorDataWrapper extends TemplateDataWrapper{

    private CreatorDataContainer data;

    public CreatorDataContainer getData() {
        return data;
    }

    public void setData(CreatorDataContainer data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CreatorDataWrapper{" +
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
