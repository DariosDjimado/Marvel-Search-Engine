package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.database.MarvelElementTable;
import fr.tse.fise2.heapoverflow.events.ComicsRequestAdaper;
import fr.tse.fise2.heapoverflow.gui.SetupView;
import fr.tse.fise2.heapoverflow.interfaces.Tasks;
import fr.tse.fise2.heapoverflow.marvelapi.*;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

/**
 * @author Darios DJIMADO
 */
public final class FetchAllComicsTask extends ComicsRequestAdaper implements Tasks {
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchAllComicsTask.class);
    private static int offset = 0;
    private final SetupView view;
    private final MarvelRequest request;

    public FetchAllComicsTask(@NotNull SetupView view) {
        this.view = view;
        request = MarvelRequest.getInstance();
    }

    @Override
    public void doTask() throws IOException {
        asyncDownloadComics();
    }

    private void asyncDownloadComics() throws IOException {
        view.onLog("Fetching comics : offset=" + offset + "&limit=100...");
        request.asyncGetData("comics", "offset=" + offset + "&limit=100", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                view.onLog("An error occurred " + e.getMessage());
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e.getMessage(), e);
                }
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                ResponseBody body = res.body();
                if (body != null) {
                    String response = body.string();
                    ComicDataWrapper comicDataWrapper = deserializeComics(response);
                    if (comicDataWrapper != null) {
                        ComicDataContainer dataContainer = comicDataWrapper.getData();
                        Comic[] comics = dataContainer.getResults();
                        onFetchedComics(comics);
                        offset = dataContainer.getOffset();
                        if (offset < dataContainer.getTotal()) {
                            asyncDownloadComics();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onFetchedComics(Comic[] comics) {
        for (Comic c : comics) {
            if (MarvelElementTable.elementsExists(c.getId(), MarvelElement.COMIC) == -1) {
                view.onLog("insert comic " + c.getTitle() + " into database");
                MarvelElementTable.insertComic(c.getId(), c.getTitle().toLowerCase());
            }
        }
    }
}

