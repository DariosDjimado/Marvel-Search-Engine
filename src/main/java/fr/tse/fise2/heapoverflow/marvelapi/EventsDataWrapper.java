package fr.tse.fise2.heapoverflow.marvelapi;

public class EventsDataWrapper extends TemplateDataWrapper {
    private EventDataContainer data;

    public EventDataContainer getData() {
        return data;
    }

    public EventsDataWrapper setData(EventDataContainer data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "EventsDataWrapper{" +
                "data=" + data +
                ", attributionText='" + attributionText + '\'' +
                ", etag='" + etag + '\'' +
                ", status='" + status + '\'' +
                ", copyright='" + copyright + '\'' +
                ", code=" + code +
                ", attributionHTML='" + attributionHTML + '\'' +
                '}';
    }
}
