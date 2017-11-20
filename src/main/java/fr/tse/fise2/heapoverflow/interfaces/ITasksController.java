package fr.tse.fise2.heapoverflow.interfaces;

public interface ITasksController {
    public void emitTaskExecutionFailed(Exception e);

    public void onTaskExecutionFailed(Exception e);

    public void emitTaskSuccessfullyExecuted();

    public void onTaskSuccessfullyExecuted();
}
