package fr.tse.fise2.heapoverflow.marvelapi;

public class Image {
    private String extension;

    private String path;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Image [extension = " + extension + ", path = " + path + "]";
    }
}
