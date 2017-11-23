package fr.tse.fise2.heapoverflow.main;

public class SearchButtonLIstenner {
    private Controller controller;

    public SearchButtonLIstenner(Controller controller) {
        this.controller = controller;
    }

    public void emitNewSearch(String text){
        this.onSearch(text);
    }

    public void onSearch(String text){
        this.controller.searchStartsWith(text);
    }
}
