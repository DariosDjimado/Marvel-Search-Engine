package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.database.MarvelElementTable;
import fr.tse.fise2.heapoverflow.events.ComicsRequestAdaper;
import fr.tse.fise2.heapoverflow.interfaces.Tasks;
import fr.tse.fise2.heapoverflow.main.AppErrorHandler;
import fr.tse.fise2.heapoverflow.main.FetchData;
import fr.tse.fise2.heapoverflow.marvelapi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

public final class FetchAllComicsTask extends ComicsRequestAdaper implements Tasks {
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchAllComicsTask.class);

    FetchAllComicsTask() {
    }

    @Override
    public boolean doTask() {
        boolean isDone;
        MarvelRequest request = MarvelRequest.getInstance();
        try {
            int offset = 2000;
            do {
                if (offset == 0) {
                    String response = request.getData("comics", "offset=" + offset + "&limit=100");
                    ComicDataWrapper comicDataWrapper = deserializeComics(response);
                    ComicDataContainer dataContainer = comicDataWrapper.getData();
                    offset = offset + 100;
                } else {
                    offset += 100;
                    Thread fetchComics = new FetchData(this, "comics", "offset=" + offset + "&limit=100" + "&orderBy=title", FetchData.ComicsType.COMICS);
                    fetchComics.run();
                }
            } while (offset < 4000);

            isDone = true;

        } catch (IOException e) {
            isDone = false;
            AppErrorHandler.onError(e);
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return isDone;
    }

    @Override
    public void onFetchedComics(Comic[] comics) {
        for (Comic c : comics) {
            if (MarvelElementTable.elementsExists(c.getId(), MarvelElement.COMIC) == -1) {
                MarvelElementTable.insertComic(c.getId(), c.getTitle().toLowerCase());
            }
        }

    }
}

