package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.interfaces.CharactersRequestObserver;
import fr.tse.fise2.heapoverflow.interfaces.ComicsRequestObserver;
import fr.tse.fise2.heapoverflow.interfaces.LoggerObserver;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Fetches data from Marvel API
 *
 * @author Darios DJIMADO
 */
public class FetchData extends Thread {
    private static final Logger logger = Logger.getLogger(FetchData.class);
    private static LoggerObserver LOGGER_OBSERVER;
    private final String url;
    private final ComicsRequestObserver comicsRequestObserver;
    private final CharactersRequestObserver charactersRequestObserver;
    private ComicsType comicsType;
    private CharactersType charactersType;

    /**
     * @param comicsRequestObserver observer for comics
     * @param url                   url to use
     * @param comicsType            type of comics event that will be emitted later
     */
    public FetchData(ComicsRequestObserver comicsRequestObserver, String url, ComicsType comicsType) {
        this(comicsRequestObserver, null, url, comicsType, null);
    }

    /**
     * @param charactersRequestObserver observer for characters
     * @param url                       url to use
     * @param charactersType            type of characters event that will be emitted later
     */
    public FetchData(CharactersRequestObserver charactersRequestObserver, String url, CharactersType charactersType) {
        this(null, charactersRequestObserver, url, null, charactersType);
    }

    /**
     * @param comicsRequestObserver     observer for comics
     * @param charactersRequestObserver observer for characters
     * @param url                       url to use
     * @param comicsType                type of comics event that will be emitted later
     * @param charactersType            type of characters event that will be emitted later
     */
    public FetchData(ComicsRequestObserver comicsRequestObserver, CharactersRequestObserver charactersRequestObserver, String url, ComicsType comicsType, CharactersType charactersType) {
        this.url = url;
        this.charactersRequestObserver = charactersRequestObserver;
        this.comicsRequestObserver = comicsRequestObserver;
        this.comicsType = comicsType;
        this.charactersType = charactersType;
        LOGGER_OBSERVER = Controller.getLoggerObserver();
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
                String response = request.getData(this.url);
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
                String response = request.getData(this.url);
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
            LOGGER_OBSERVER.onError(logger, e);
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
