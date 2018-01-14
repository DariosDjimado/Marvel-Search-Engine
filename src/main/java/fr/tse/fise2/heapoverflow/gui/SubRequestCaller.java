package fr.tse.fise2.heapoverflow.gui;

import java.util.Set;

public interface SubRequestCaller {
    void updateList(Set elements, String elementType, String tab, int token, boolean end, MarvelListElement lastElement);
}
