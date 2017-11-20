package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;

public interface SearchListenner {

    public void onComicAvailable(Comic c);
    public void onCharacterAvailable(Character c);

}
