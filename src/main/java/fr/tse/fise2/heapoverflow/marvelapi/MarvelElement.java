package fr.tse.fise2.heapoverflow.marvelapi;

public enum MarvelElement {
    COMIC(MarvelElementBase.getComicMapInt()),
    CHARACTER(MarvelElementBase.getCharacterMapInt()),;

    private final int value;


    MarvelElement(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

