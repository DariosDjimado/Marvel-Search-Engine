package fr.tse.fise2.heapoverflow.database;

import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.ComicDataWrapper;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.Assert.*;

public class DataBaseTest {
    @Test
    public void saveAndGetComicNamesTest() throws Exception {
        MarvelRequest request = new MarvelRequest();
        try {
            String response = request.getData("comics?limit=100");
            ComicDataWrapper comicDataWrapper = MarvelRequest.deserializeComics(response);

            Comic[] comics = comicDataWrapper.getData().getResults();

            DataBase.saveComicNames(comicDataWrapper.getData().getResults());

            List<String> lists = DataBase.getComicNames();
            assertEquals(lists.size(),comics.length);
            for(int i = 0; i<comics.length;i++) {
                assertEquals(lists.get(i),comics[i].getTitle());
            }

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}