package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.database.CharactersTable;
import fr.tse.fise2.heapoverflow.gui.UI;
import fr.tse.fise2.heapoverflow.interfaces.Tasks;
import fr.tse.fise2.heapoverflow.main.Controller;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.CharacterDataContainer;
import fr.tse.fise2.heapoverflow.marvelapi.CharacterDataWrapper;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeCharacters;

public class FetchAllCharactersTask implements Tasks {
    private final TasksController tasksController;

    public FetchAllCharactersTask(TasksController tasksController) {
        this.tasksController = tasksController;
    }

    @Override
    public boolean doTask() {
        boolean isDone;
        CharactersTable charactersTable = this.tasksController.getController().getCharactersTable();
        MarvelRequest request = new MarvelRequest();

        try {
            int offset = 0;
            int total;
            do {
                String response = request.getData("characters?offset=" + offset + "&limit=100");
                CharacterDataWrapper characterDataWrapper = deserializeCharacters(response);
                CharacterDataContainer dataContainer = characterDataWrapper.getData();

                offset = offset + 100;
                total = dataContainer.getTotal();

                for (Character c : dataContainer.getResults()) {
                    charactersTable.insertIntoCharacters(c.getId(), c.getName().toLowerCase());
                }

            } while (offset < total);

            isDone = true;

        } catch (IOException | NoSuchAlgorithmException | SQLException e) {
            isDone = false;
            this.tasksController.emitTaskExecutionFailed(e);
        }
        return isDone;
    }

    public static void main(String[] args) {
        UI ui = new UI();
        Controller controller = new Controller(ui);
        TasksController tasksController = new TasksController(controller);

        FetchAllCharactersTask fetchAllCharactersTask = new FetchAllCharactersTask(tasksController);
        fetchAllCharactersTask.doTask();

    }
}
