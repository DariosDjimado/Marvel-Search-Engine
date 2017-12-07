package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.database.CreateTables;
import fr.tse.fise2.heapoverflow.gui.UI;
import fr.tse.fise2.heapoverflow.main.AppLogger;
import fr.tse.fise2.heapoverflow.main.Controller;

import java.awt.*;

public class TaskSetup {
    public static void main(String[] args) {
        TaskSetup taskSetup = new TaskSetup();

        taskSetup.init();
    }

    private void init() {
        UI ui = new UI();

        EventQueue.invokeLater(ui::init);
        AppLogger appLogger = new AppLogger(ui);
        appLogger.configureLogging();
        Controller controller = new Controller(ui, appLogger);

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
