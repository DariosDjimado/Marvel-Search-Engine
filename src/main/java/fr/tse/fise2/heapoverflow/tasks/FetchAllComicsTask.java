package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.database.ComicsTable;
import fr.tse.fise2.heapoverflow.gui.UI;
import fr.tse.fise2.heapoverflow.interfaces.Tasks;
import fr.tse.fise2.heapoverflow.main.Controller;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.ComicDataContainer;
import fr.tse.fise2.heapoverflow.marvelapi.ComicDataWrapper;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static fr.tse.fise2.heapoverflow.marvelapi.MarvelRequest.deserializeComics;

//import fr.tse.fise2.heapoverflow.database.ComicRow;

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
            int offset = 101;
            int total;
            do {
                System.out.println(offset);
                String response = request.getData("comics?offset=" + offset + "&limit=100");
                ComicDataWrapper comicDataWrapper = deserializeComics(response);
                ComicDataContainer dataContainer = comicDataWrapper.getData();


                offset = offset + 100;
                total = dataContainer.getTotal();

                for (Comic c : dataContainer.getResults()) {
                   if(!comicsTable.exists(c.getId())){
                       comicsTable.insertIntoComics(c.getId(), c.getTitle().toLowerCase());
                   } else{
                       System.out.println("dup");
                   }
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
        UI ui = new UI();

        Controller controller = new Controller(ui);
        TasksController tasksController = new TasksController(controller);
        FetchAllComicsTask fetchAllComicsTask = new FetchAllComicsTask(tasksController);
        fetchAllComicsTask.doTask();

    }
}

