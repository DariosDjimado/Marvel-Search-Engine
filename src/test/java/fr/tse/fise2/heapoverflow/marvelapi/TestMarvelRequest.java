package fr.tse.fise2.heapoverflow.marvelapi;

import fr.tse.fise2.heapoverflow.main.AppConfig;
import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeCharacters;
import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Darios DJIMADO
 */
public class TestMarvelRequest {

    @BeforeClass
    public static void setupConfig() {
        AppErrorHandler.configureLogging();
    }


    @Test
    public void TestDeserializeCharacters() {

        MarvelRequest request = MarvelRequest.getInstance();

        try {
            // fetch all characters
            String response = request.getData("characters", null);
            // deserialize the response
            CharacterDataWrapper characterDataWrapper = deserializeCharacters(response);
            // we must have an instance of CharacterDataWrapper
            assertNotNull(characterDataWrapper);
            // the code of response must be 200
            assertEquals(characterDataWrapper.getCode(), 200);
            // should contain at least one result
            assertNotEquals(characterDataWrapper.getData().getResults().length, 0);


            // fetch characters using query
            String responseToQuery = request.getData("characters", "nameStartsWith=th");
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
            String responseToId = request.getData("characters/1011334", null);
            // deserialize the response
            CharacterDataWrapper characterDataWrapperToId = deserializeCharacters(responseToId);
            // the code of response must be 200
            assertEquals(characterDataWrapperToId.getCode(), 200);

            Character[] resultsToId = characterDataWrapperToId.getData().getResults();
            // resultToId should have only one comic
            assertEquals(resultsToId.length, 1);

            // check that the comic sent is the good one
            assertEquals(resultsToId[0].getId(), 1011334);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestDeserializeComics() {

        MarvelRequest request = MarvelRequest.getInstance();

        try {
            // fetch all comics
            String response = request.getData("comics", null);
            // deserialize the response
            ComicDataWrapper comicDataWrapper = deserializeComics(response);
            // we must have an instance of ComicDataWrapper
            assertNotNull(comicDataWrapper);
            // the code of response must be 200
            assertEquals(comicDataWrapper.getCode(), 200);
            /// should contain at least one result
            assertNotNull(comicDataWrapper.getData().getResults());


            // fetch comic using query
            String responseToQuery = request.getData("comics", "titleStartsWith=th");
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
            String responseToId = request.getData("comics/21486", null);
            // deserialize the response
            ComicDataWrapper comicDataWrapperToId = deserializeComics(responseToId);
            // the code of response must be 200
            assertEquals(comicDataWrapperToId.getCode(), 200);

            Comic[] resultsToId = comicDataWrapperToId.getData().getResults();
            // resultToId should have only one comic
            assertEquals(resultsToId.length, 1);

            // check that the comic sent is the good one
            assertEquals(resultsToId[0].getId(), 21486);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestGetData() throws Exception {
        MarvelRequest request = MarvelRequest.getInstance();
        String data = request.getData("creators", null);
        assertNotNull(data);
        assertTrue(data.contains("\"code\":200,\"status\":\"Ok\",\"copyright\":\"© 2017 MARVEL"));
    }

    @Test
    public void TestGetImage() throws Exception {
        // create an image of type : Marvel Image
        String path = "http://i.annihil.us/u/prod/marvel/i/mg/9/f0/589deb5c4a277";
        String extension = "jpg";
        Image image = new Image();
        image.setPath(path);
        image.setExtension(extension);

        // get url
        BufferedImage bufferedImage = MarvelRequest.getImage(image, UrlBuilder.ImageVariant.PORTRAIT_FANTASTIC, AppConfig.getInstance().getTmpDir(), null);

        assertNotNull(bufferedImage);
        assertEquals(bufferedImage.getHeight(), 252);
        assertEquals(bufferedImage.getWidth(), 168);
    }

}
