package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.database.ComicsTable;
import fr.tse.fise2.heapoverflow.main.Controller;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.ComicDataContainer;
import fr.tse.fise2.heapoverflow.marvelapi.ComicDataWrapper;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;
//import fr.tse.fise2.heapoverflow.database.ComicRow;
import fr.tse.fise2.heapoverflow.interfaces.Tasks;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

public class FetchAllComicsTask implements Tasks{
    private final TasksController tasksController;

    public FetchAllComicsTask(TasksController tasksController) {
        this.tasksController = tasksController;
    }

    @Override
    public boolean doTask() {
        boolean isDone;
        ComicsTable comicsTable = this.tasksController.getController().getComicsTable();
        MarvelRequest request = new MarvelRequest();

        try {
            int offset = 900;
            int total;
            do {
                String response = request.getData("comics?offset=" + offset + "&limit=100");
                ComicDataWrapper comicDataWrapper = deserializeComics(response);

                ComicDataContainer dataContainer = comicDataWrapper.getData();
                offset = offset + 100;
                total = dataContainer.getTotal();

                for (Comic c : dataContainer.getResults()) {
                    comicsTable.insertIntoComics(c.getId(), c.getTitle().toLowerCase());
                    System.out.println(c.getTitle());
                }

            } while (offset < 2000);

            isDone = true;

        } catch (IOException | NoSuchAlgorithmException | SQLException e) {
            isDone = false;
            e.printStackTrace();
        }
        return isDone;
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        TasksController tasksController = new TasksController(controller);
        FetchAllComicsTask fetchAllComicsTask = new FetchAllComicsTask(tasksController);
        fetchAllComicsTask.doTask();

    }
}

