package fr.tse.fise2.heapoverflow.interfaces;

import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;

public interface ISelectionChangedListener {

    void showComic(Comic comic);

    void showCharacter(Character character);
}
