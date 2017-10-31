package fr.tse.fise2.heapoverflow.marvelapi;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.*;
import static org.junit.Assert.*;

public class TestMarvelRequest {
    @Test
    public void TestDeserializeCharacters() {

        MarvelRequest request = new MarvelRequest();

        try {
            // fetch all characters
            String response = request.getData("characters");
            // deserialize the response
            CharacterDataWrapper characterDataWrapper = deserializeCharacters(response);
            // we must have an instance of CharacterDataWrapper
            assertNotNull(characterDataWrapper);
            // the code of response must be 200
            assertEquals(characterDataWrapper.getCode(), 200);
            // should contain at least one result
            assertNotEquals(characterDataWrapper.getData().getResults().length, 0);


            // fetch characters using query
            String responseToQuery = request.getData("characters?nameStartsWith=th");
            // deserialize the response
            CharacterDataWrapper characterDataWrapperToQuery = deserializeCharacters(responseToQuery);
            // the code of response must be 200
            assertEquals(characterDataWrapperToQuery.getCode(), 200);

            Character[] resultsToQuery = characterDataWrapperToQuery.getData().getResults();
            for (Character aResultToQuery : resultsToQuery) {
                // each title should start by 'th'
                assertEquals(aResultToQuery.getName().toLowerCase().substring(0, 2), "th");
            }


            // fetch comic using id
            String responseToId = request.getData("characters/1011334");
            // deserialize the response
            CharacterDataWrapper characterDataWrapperToId = deserializeCharacters(responseToId);
            // the code of response must be 200
            assertEquals(characterDataWrapperToId.getCode(), 200);

            Character[] resultsToId = characterDataWrapperToId.getData().getResults();
            // resultToId should have only one comic
            assertEquals(resultsToId.length, 1);

            // check that the comic sent is the good one
            assertEquals(resultsToId[0].getId(), 1011334);

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestDeserializeComics() {

        MarvelRequest request = new MarvelRequest();

        try {
            // fetch all comics
            String response = request.getData("comics");
            // deserialize the response
            ComicDataWrapper comicDataWrapper = deserializeComics(response);
            // we must have an instance of ComicDataWrapper
            assertNotNull(comicDataWrapper);
            // the code of response must be 200
            assertEquals(comicDataWrapper.getCode(), 200);
            /// should contain at least one result
            assertNotNull(comicDataWrapper.getData().getResults());


            // fetch comic using query
            String responseToQuery = request.getData("comics?titleStartsWith=th");
            // deserialize the response
            ComicDataWrapper comicDataWrapperToQuery = deserializeComics(responseToQuery);
            // the code of response must be 200
            assertEquals(comicDataWrapperToQuery.getCode(), 200);

            Comic[] resultsToQuery = comicDataWrapperToQuery.getData().getResults();
            for (Comic aResultToQuery : resultsToQuery) {
                // each title should start by 'th'
                assertEquals(aResultToQuery.getTitle().toLowerCase().substring(0, 2), "th");
            }


            // fetch comic using id
            String responseToId = request.getData("comics/21486");
            // deserialize the response
            ComicDataWrapper comicDataWrapperToId = deserializeComics(responseToId);
            // the code of response must be 200
            assertEquals(comicDataWrapperToId.getCode(), 200);

            Comic[] resultsToId = comicDataWrapperToId.getData().getResults();
            // resultToId should have only one comic
            assertEquals(resultsToId.length, 1);

            // check that the comic sent is the good one
            assertEquals(resultsToId[0].getId(), 21486);


        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestGetData() throws Exception {
        MarvelRequest request = new MarvelRequest();

        // fetch data
        try {
            String data = request.getData("creators");
            assertTrue(data.contains("\"code\":200,\"status\":\"Ok\",\"copyright\":\"© 2017 MARVEL"));

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void TestGetImage() throws Exception {
        MarvelRequest request = new MarvelRequest();

        // fetch comics
        String responseComics = request.getData("comics");
        // find an existing image
        Image comicImage = deserializeComics(responseComics).getData().getResults()[2].getImages()[0];
        // fetch the image
        BufferedImage bufferedImage = getImage(comicImage, ImageVariant.PORTRAIT_MEDIUM);

        assertEquals(bufferedImage.getHeight(), 150);
        assertEquals(bufferedImage.getWidth(), 100);

    }

}
