package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * This class consists exclusively of  public methods that return or set protected fields. The fields are common of all
 * class *.Summary. This POJO doesn't contain the override toString method.
 */
public class TemplateSummary {

    protected String resourceURI;

    protected String name;

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
