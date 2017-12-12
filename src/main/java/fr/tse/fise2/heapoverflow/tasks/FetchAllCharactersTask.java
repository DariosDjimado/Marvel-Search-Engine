package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.database.MarvelElementTable;
import fr.tse.fise2.heapoverflow.interfaces.CharactersRequestObserver;
import fr.tse.fise2.heapoverflow.interfaces.Tasks;
import fr.tse.fise2.heapoverflow.main.FetchData;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.CharacterDataContainer;
import fr.tse.fise2.heapoverflow.marvelapi.CharacterDataWrapper;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;

import java.io.IOException;
import java.sql.SQLException;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeCharacters;


public class FetchAllCharactersTask implements Tasks, CharactersRequestObserver {

    public FetchAllCharactersTask() {
    }

    @Override
    public boolean doTask() {
        boolean isDone;
        MarvelRequest request = new MarvelRequest();

        try {
            int offset = 0;
            int total = 0;
            do {
                if (offset == 0) {
                    String response = request.getData("characters?offset=" + offset + "&limit=100");
                    CharacterDataWrapper characterDataWrapper = deserializeCharacters(response);
                    CharacterDataContainer dataContainer = characterDataWrapper.getData();

                    total = dataContainer.getTotal();
                    offset = offset + 100;

                } else {
                    offset += 100;
                    Thread fetchCharacters = new FetchData(this, "characters?offset=" + offset + "&limit=100" + "&orderBy=name", FetchData.CharactersType.CHARACTERS);
                    fetchCharacters.run();
                }


            } while (offset < total);

            isDone = true;

        } catch (IOException e) {
            isDone = false;
        }
        return isDone;
    }

    @Override
    public void onFetchingCharacters(String Url) {

    }

    @Override
    public void onFetchedCharacters(Character[] characters) {
        for (Character c : characters) {
            try {
                MarvelElementTable.insertCharacter(c.getId(), c.getName().toLowerCase());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFetchedCharactersById(Character character) {

    }

    @Override
    public void onFetchedCharactersInSameComic(Character[] characters) {

    }
}
