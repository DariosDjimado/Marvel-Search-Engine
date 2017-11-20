package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.interfaces.ITasksController;
import fr.tse.fise2.heapoverflow.main.Controller;

public class TasksController implements ITasksController {
    private final Controller controller;

    public TasksController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void emitTaskExecutionFailed(Exception e) {

    }

    @Override
    public void onTaskExecutionFailed(Exception e) {

    }

    @Override
    public void emitTaskSuccessfullyExecuted() {

    }

    @Override
    public void onTaskSuccessfullyExecuted() {

    }

    public Controller getController() {
        return controller;
    }
}
