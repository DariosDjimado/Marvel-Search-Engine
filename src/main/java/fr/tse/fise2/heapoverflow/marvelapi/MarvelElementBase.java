package fr.tse.fise2.heapoverflow.marvelapi;

public class MarvelElementBase {

    private final static int comicMapInt = 0;
    private final static int characterMapInt = 1;

    public static int getComicMapInt() {
        return comicMapInt;
    }

    public static int getCharacterMapInt() {
        return characterMapInt;
    }

    public static MarvelElement convertToMarvelElement(int mapInt) {
        MarvelElement marvelElement = null;
        switch (mapInt) {
            case comicMapInt: {
                marvelElement = MarvelElement.COMIC;
                break;
            }
            case characterMapInt: {
                marvelElement = MarvelElement.CHARACTER;
                break;
            }
            default:
                break;
        }
        return marvelElement;
    }

}
