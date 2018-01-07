package fr.tse.fise2.heapoverflow.gui;

/**
 * Class to adapt marvel API elements for JList display and use
 * TODO: Make it public
 * @author Théo Basty
 */
public class MarvelListElement{
    String dispName;
    String shortURI;
    MarvelType type;

    public MarvelListElement(String dispName, String shortURI, MarvelType type) {
        this.dispName = dispName;
        if(shortURI != null && shortURI.substring(0, 4).equals("http")){
            this.shortURI = shortURI.substring(36);
        }
        else {
            this.shortURI = shortURI;
        }
        this.type = type;
    }

    public String getDispName() {
        return dispName;
    }

    public void setDispName(String dispName) {
        this.dispName = dispName;
    }

    public String getShortURI() {
        return shortURI;
    }

    public void setShortURI(String shortURI) {
        this.shortURI = shortURI;
    }

    public MarvelType getType() {
        return type;
    }

    public void setType(MarvelType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return dispName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarvelListElement that = (MarvelListElement) o;

        if (dispName != null ? !dispName.equals(that.dispName) : that.dispName != null) return false;
        if (shortURI != null ? !shortURI.equals(that.shortURI) : that.shortURI != null) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = dispName != null ? dispName.hashCode() : 0;
        result = 31 * result + (shortURI != null ? shortURI.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
