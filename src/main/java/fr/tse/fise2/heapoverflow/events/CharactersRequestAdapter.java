package fr.tse.fise2.heapoverflow.events;

import fr.tse.fise2.heapoverflow.interfaces.CharactersRequestObserver;
import fr.tse.fise2.heapoverflow.marvelapi.Character;

/**
 * @author Darios DJIMADO
 */
public abstract class CharactersRequestAdapter implements CharactersRequestObserver {
    @Override
    public void onFetchingCharacters(String Url) {
    }

    @Override
    public void onFetchedCharacters(Character[] characters) {
    }

    @Override
    public void onFetchedCharactersById(Character character) {
    }

    @Override
    public void onFetchedCharactersInSameComic(Character[] characters) {
    }
}
