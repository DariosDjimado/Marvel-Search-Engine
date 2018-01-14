package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * CharacterDataWrapper POJO
 *
 * @author Darios DJIMADO
 */
public class CharacterDataWrapper extends TemplateDataWrapper {

    private CharacterDataContainer data;

    public CharacterDataContainer getData() {
        return data;
    }

    public void setData(CharacterDataContainer data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CharacterDataWrapper{" +
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
