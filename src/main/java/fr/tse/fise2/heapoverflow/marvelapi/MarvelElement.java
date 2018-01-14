package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * MarvelElement is an enum used to switch between comis and character.
 *
 * @author Darios DJIMADO
 */
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

