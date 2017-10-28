package fr.tse.fise2.heapoverflow.marvelapi;

public class CharacterDataWrapper {
    private String attributionText;

    private String etag;

    private String status;

    private CharacterDataContainer data;

    private String copyright;

    private String code;

    private String attributionHTML;

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CharacterDataContainer getData() {
        return data;
    }

    public void setData(CharacterDataContainer data) {
        this.data = data;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAttributionHTML() {
        return attributionHTML;
    }

    public void setAttributionHTML(String attributionHTML) {
        this.attributionHTML = attributionHTML;
    }

    @Override
    public String toString() {
        return "CharacterDataWrapper [attributionText = " + attributionText + ", etag = " + etag + ", status = " +
                status + ", data = " + data + ", copyright = " + copyright + ", code = " + code + ", attributionHTML = "
                + attributionHTML + "]";
    }
}
