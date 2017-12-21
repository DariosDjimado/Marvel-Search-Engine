package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.database.CreateTables;
import fr.tse.fise2.heapoverflow.main.AppErrorHandler;

public class TaskSetup {
    public static void main(String[] args) {
        TaskSetup taskSetup = new TaskSetup();
        AppErrorHandler.configureLogging();
        taskSetup.init();
    }

    private void init() {
        TasksController tasksController = new TasksController();

        // create tables
        System.out.println("creating tables");
        boolean created = CreateTables.createAllTables();
        System.out.println(created ? "tables successfully created" : "an error occurred when creating tables");

        // fetch data
        FetchAllCharactersTask fetchAllCharactersTask = new FetchAllCharactersTask();
        FetchAllComicsTask fetchAllComicsTask = new FetchAllComicsTask();

        System.out.println("fetching characters");
        fetchAllCharactersTask.doTask();
        System.out.println("fetching comics");
        fetchAllComicsTask.doTask();

    }
}
