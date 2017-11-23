package fr.tse.fise2.heapoverflow.main;

public class SelectionChangedListenner {

    private Controller controller;

    public SelectionChangedListenner(Controller controller) {
        this.controller = controller;
    }

    public void emitSelectionChanged(String name){
        this.onSelectionChanged(name);
    }

    public void onSelectionChanged(String name){
        this.controller.onSelectionChanged(name);
    }
}
