package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.database.CreateTables;

public class TaskSetup {
    public static void main(String[] args) {
        TaskSetup taskSetup = new TaskSetup();

        taskSetup.init();
    }

    private void init() {
        TasksController tasksController = new TasksController();

        // create tables
        CreateTables tables = new CreateTables(tasksController.getConnectionDB());
        System.out.println("creating tables");
        boolean created = tables.createAllTables();
        System.out.println(created ? "tables successfully created" : "an error occurred when creating tables");

        // fetch data
        FetchAllCharactersTask fetchAllCharactersTask = new FetchAllCharactersTask(tasksController);
        FetchAllComicsTask fetchAllComicsTask = new FetchAllComicsTask(tasksController);

        System.out.println("fetching characters");
        fetchAllCharactersTask.doTask();
        System.out.println("fetching comics");
        fetchAllComicsTask.doTask();

    }
}
