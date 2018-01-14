package fr.tse.fise2.heapoverflow.gui;

class SearchHandler {
    private static String currentSearch;

    public static String getCurrentSearch() {
        return currentSearch;
    }

    public static void setCurrentSearch(String currentSearch) {
        SearchHandler.currentSearch = currentSearch;
    }
}
