package fr.tse.fise2.heapoverflow.marvelapi;

import java.util.Arrays;

public class Character {
    private String id;

    private SeriesList series;

    private StoryList stories;

    private Image thumbnail;

    private String resourceURI;

    private EventList events;

    private Url[] urls;

    private String description;

    private String name;

    private ComicList comics;

    private String modified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SeriesList getSeries() {
        return series;
    }

    public void setSeries(SeriesList series) {
        this.series = series;
    }

    public StoryList getStories() {
        return stories;
    }

    public void setStories(StoryList stories) {
        this.stories = stories;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public EventList getEvents() {
        return events;
    }

    public void setEvents(EventList events) {
        this.events = events;
    }

    public Url[] getUrls() {
        return urls;
    }

    public void setUrls(Url[] urls) {
        this.urls = urls;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComicList getComics() {
        return comics;
    }

    public void setComics(ComicList comics) {
        this.comics = comics;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "Character [id = " + id + ", series = " + series + ", stories = " + stories + ", thumbnail = "
                + thumbnail + ", resourceURI = " + resourceURI + ", events = " + events + ", urls = "
                + Arrays.toString(urls) + ", description = " + description + ", name = " + name + ", comics = "
                + comics + ", modified = " + modified + "]";
    }
}
