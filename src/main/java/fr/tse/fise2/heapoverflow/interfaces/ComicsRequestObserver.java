package fr.tse.fise2.heapoverflow.interfaces;

import fr.tse.fise2.heapoverflow.marvelapi.Comic;

public interface ComicsRequestObserver {

    void onFetchingComics(String url);

    void onFetchedComics(Comic[] comics);

    void onFetchedComicById(Comic comic);

    void onFetchedComicsInSameSeries(Comic[] comics);
}
