package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.database.MarvelElementTable;
import fr.tse.fise2.heapoverflow.events.CharactersRequestAdapter;
import fr.tse.fise2.heapoverflow.gui.SetupView;
import fr.tse.fise2.heapoverflow.interfaces.Tasks;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.CharacterDataContainer;
import fr.tse.fise2.heapoverflow.marvelapi.CharacterDataWrapper;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeCharacters;

/**
 * @author Darios DJIMADO
 */
public class FetchAllCharactersTask extends CharactersRequestAdapter implements Tasks {
    private static int offset = 0;
    private final SetupView view;
    private final MarvelRequest request;

    public FetchAllCharactersTask(@NotNull SetupView view) {
        this.view = view;
        request = MarvelRequest.getInstance();
    }

    @Override
    public void doTask() throws IOException {
        asyncDownloadCharacters();
    }

    @Override
    public void onFetchedCharacters(Character[] characters) {
        for (Character c : characters) {
            MarvelElementTable.insertCharacter(c.getId(), c.getName().toLowerCase());
        }
    }


    private void asyncDownloadCharacters() throws IOException {
        view.onLog("Fetching comics : offset=" + offset + "&limit=100...");
        request.asyncGetData("characters", "offset=" + offset + "&limit=100", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                view.onLog("An error occurred " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                ResponseBody body = res.body();
                if (body != null) {
                    String response = body.string();
                    CharacterDataWrapper characterDataWrapper = deserializeCharacters(response);
                    if (characterDataWrapper != null) {
                        CharacterDataContainer dataContainer = characterDataWrapper.getData();
                        Character[] characters = dataContainer.getResults();
                        onFetchedCharacters(characters);
                        offset = dataContainer.getOffset();
                        if (offset < dataContainer.getTotal()) {
                            asyncDownloadCharacters();
                        }
                    }
                }
            }
        });

    }
}
