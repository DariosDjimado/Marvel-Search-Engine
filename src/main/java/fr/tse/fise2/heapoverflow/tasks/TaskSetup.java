package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.main.AppErrorHandler;

class TaskSetup {
    public static void main(String[] args) {
        TaskSetup taskSetup = new TaskSetup();
        AppErrorHandler.configureLogging();
        taskSetup.init();
    }

    private void init() {
        // fetch data
        FetchAllCharactersTask fetchAllCharactersTask = new FetchAllCharactersTask();
        FetchAllComicsTask fetchAllComicsTask = new FetchAllComicsTask();

        fetchAllCharactersTask.doTask();
        fetchAllComicsTask.doTask();

    }
}
