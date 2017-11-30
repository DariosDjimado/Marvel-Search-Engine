package fr.tse.fise2.heapoverflow.events;

import fr.tse.fise2.heapoverflow.main.Controller;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SearchButtonListener {
    private Controller controller;

    public SearchButtonListener(Controller controller) {
        this.controller = controller;
    }

    public void emitNewSearch(String text){
        this.onSearch(text);
    }

    public void onSearch(String text){
        this.controller.searchStartsWith(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SearchButtonListener)) return false;

        SearchButtonListener that = (SearchButtonListener) o;

        return new EqualsBuilder()
                .append(controller, that.controller)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(controller)
                .toHashCode();
    }
}
