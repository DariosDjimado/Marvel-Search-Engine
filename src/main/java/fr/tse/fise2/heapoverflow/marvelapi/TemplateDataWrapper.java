package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * This class consists exclusively of  public methods that return or set protected fields. The fields are common of all
 * class *.DataWrapper. This POJO doesn't contain the override toString method.
 */
public class TemplateDataWrapper {
    protected String attributionText;

    protected String etag;

    protected String status;

    protected String copyright;

    protected int code;

    protected String attributionHTML;

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

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAttributionHTML() {
        return attributionHTML;
    }

    public void setAttributionHTML(String attributionHTML) {
        this.attributionHTML = attributionHTML;
    }
}
