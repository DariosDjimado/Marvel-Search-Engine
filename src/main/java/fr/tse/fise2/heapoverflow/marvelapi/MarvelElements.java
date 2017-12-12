package fr.tse.fise2.heapoverflow.marvelapi;

public enum MarvelElements {
    COMIC(0),
    CHARACTER(1),;

    private final int value;


    MarvelElements(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
