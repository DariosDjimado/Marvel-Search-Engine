package fr.tse.fise2.heapoverflow.events;

import fr.tse.fise2.heapoverflow.interfaces.IRequestListener;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RequestListener {
    private final IRequestListener requestListener;

    public RequestListener(IRequestListener requestListener) {
        this.requestListener = requestListener;
    }

    public void startLoading(String name) {
        this.requestListener.startLoading(name);
    }

    public void endLoading(String name) {
        this.requestListener.endLoading(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof RequestListener)) return false;

        RequestListener that = (RequestListener) o;

        return new EqualsBuilder()
                .append(requestListener, that.requestListener)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(requestListener)
                .toHashCode();
    }
}
