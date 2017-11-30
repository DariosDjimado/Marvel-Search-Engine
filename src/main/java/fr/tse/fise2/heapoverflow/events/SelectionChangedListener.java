package fr.tse.fise2.heapoverflow.events;

import fr.tse.fise2.heapoverflow.interfaces.ISelectionChangedListener;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SelectionChangedListener implements ISelectionChangedListener {

    private final ISelectionChangedListener listener;

    public SelectionChangedListener(ISelectionChangedListener listener) {
        this.listener = listener;
    }


    @Override
    public void showComic(Comic comic) {
        this.listener.showComic(comic);
    }

    @Override
    public void showCharacter(Character character) {
        this.listener.showCharacter(character);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SelectionChangedListener)) return false;

        SelectionChangedListener that = (SelectionChangedListener) o;

        return new EqualsBuilder()
                .append(listener, that.listener)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(listener)
                .toHashCode();
    }
}
