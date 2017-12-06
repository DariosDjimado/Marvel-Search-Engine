package fr.tse.fise2.heapoverflow.interfaces;

import fr.tse.fise2.heapoverflow.marvelapi.Character;

public interface CharactersRequestObserver {

    void onFetchingCharacters(String Url);

    void onFetchedCharacters(Character[] characters);

    void onFetchedCharactersById(Character character);

    void onFetchedCharactersInSameComic(Character[] characters);
}
