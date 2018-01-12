package fr.tse.fise2.heapoverflow.gui;

public class EmptyListElement implements MarvelListElement {
    @Override
    public Object getDispedO() {
        return null;
    }

    @Override
    public String toString() {
        return "<Empty>";
    }
}
