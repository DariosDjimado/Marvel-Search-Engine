package fr.tse.fise2.heapoverflow.events;

import fr.tse.fise2.heapoverflow.interfaces.ComicsRequestObserver;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;

/**
 * @author Darios DJIMADO
 */
public abstract class ComicsRequestAdaper implements ComicsRequestObserver {
    @Override
    public void onFetchingComics(String url) {
    }

    @Override
    public void onFetchedComics(Comic[] comics) {
    }

    @Override
    public void onFetchedComicById(Comic comic) {
    }

    @Override
    public void onFetchedComicsInSameSeries(Comic[] comics) {
    }
}
