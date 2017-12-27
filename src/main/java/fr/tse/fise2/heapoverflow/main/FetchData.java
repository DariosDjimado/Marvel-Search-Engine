package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.interfaces.CharactersRequestObserver;
import fr.tse.fise2.heapoverflow.interfaces.ComicsRequestObserver;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Fetches data from Marvel API
 *
 * @author Darios DJIMADO
 */
public class FetchData extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchData.class);
    private final String url;
    private final String query;
    private final ComicsRequestObserver comicsRequestObserver;
    private final CharactersRequestObserver charactersRequestObserver;
    private ComicsType comicsType;
    private CharactersType charactersType;

    /**
     * @param comicsRequestObserver observer for comics
     * @param url                   url to use
     * @param query                 query can be null
     * @param comicsType            type of comics event that will be emitted later
     */
    public FetchData(ComicsRequestObserver comicsRequestObserver, String url, String query, ComicsType comicsType) {
        this(comicsRequestObserver, null, url, query, comicsType, null);
    }

    /**
     * @param charactersRequestObserver observer for characters
     * @param url                       url to use
     * @param query                     query can be null
     * @param charactersType            type of characters event that will be emitted later
     */
    public FetchData(CharactersRequestObserver charactersRequestObserver, String url, String query, CharactersType charactersType) {
        this(null, charactersRequestObserver, url, query, null, charactersType);
    }

    /**
     * @param comicsRequestObserver     observer for comics
     * @param charactersRequestObserver observer for characters
     * @param url                       url to use
     * @param query                     query can be null
     * @param comicsType                type of comics event that will be emitted later
     * @param charactersType            type of characters event that will be emitted later
     */
    private FetchData(ComicsRequestObserver comicsRequestObserver, CharactersRequestObserver charactersRequestObserver, String url, String query, ComicsType comicsType, CharactersType charactersType) {
        this.url = url;
        this.query = query;
        this.charactersRequestObserver = charactersRequestObserver;
        this.comicsRequestObserver = comicsRequestObserver;
        this.comicsType = comicsType;
        this.charactersType = charactersType;
    }

    /**
     * Runs the thread. Fetch data and emits event when it is done
     */
    @Override
    public void run() {
        MarvelRequest request = new MarvelRequest();
        try {
            if (this.comicsRequestObserver != null) {
                // emit on fetching event
                this.comicsRequestObserver.onFetchingComics(this.url);
                String response = request.getData(this.url, this.query);
                switch (this.comicsType) {
                    case COMICS:
                        this.comicsRequestObserver.onFetchedComics(MarvelRequest.deserializeComics(response).getData().getResults());
                        break;
                    case COMIC_BY_ID:
                        this.comicsRequestObserver.onFetchedComicById(MarvelRequest.deserializeComics(response).getData().getResults()[0]);
                        break;
                    case COMICS_IN_SAME_SERIES:
                        this.comicsRequestObserver.onFetchedComicsInSameSeries(MarvelRequest.deserializeComics(response).getData().getResults());
                        break;
                    default:
                        break;
                }
            }
            if (this.charactersRequestObserver != null) {
                // emit on fetching event
                this.charactersRequestObserver.onFetchingCharacters(this.url);
                String response = request.getData(this.url, this.query);
                switch (this.charactersType) {
                    case CHARACTERS:
                        this.charactersRequestObserver.onFetchedCharacters(MarvelRequest.deserializeCharacters(response).getData().getResults());
                        break;
                    case CHARACTER_BY_ID:
                        this.charactersRequestObserver.onFetchedCharactersById(MarvelRequest.deserializeCharacters(response).getData().getResults()[0]);
                        break;
                    case CHARACTERS_IN_SAME_SERIES:
                        this.charactersRequestObserver.onFetchedCharactersInSameComic(MarvelRequest.deserializeCharacters(response).getData().getResults());
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            AppErrorHandler.onError(e);
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage(), e);
            }
        }

    }

    /**
     * Comics events type
     */
    public enum ComicsType {
        COMICS,
        COMIC_BY_ID,
        COMICS_IN_SAME_SERIES,
    }

    /**
     * Characters events type
     */
    public enum CharactersType {
        CHARACTERS,
        CHARACTER_BY_ID,
        CHARACTERS_IN_SAME_SERIES,
    }

}
