package fr.tse.fise2.heapoverflow.marvelapi;


import java.util.Arrays;

public class Serie {

    private int endYear;

    private SeriesSummary previous;

    private EventList events;

    private Url[] urls;

    private SeriesSummary next;

    private String modified;

    private CreatorList creators;

    private int id;

    private String title;

    private Image thumbnail;

    private StoryList stories;

    private String resourceURI;

    private int startYear;

    private String description;

    private String rating;

    private ComicList comics;

    private CharacterList characters;

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public SeriesSummary getPrevious() {
        return previous;
    }

    public void setPrevious(SeriesSummary previous) {
        this.previous = previous;
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

    public SeriesSummary getNext() {
        return next;
    }

    public void setNext(SeriesSummary next) {
        this.next = next;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public CreatorList getCreators() {
        return creators;
    }

    public void setCreators(CreatorList creators) {
        this.creators = creators;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public StoryList getStories() {
        return stories;
    }

    public void setStories(StoryList stories) {
        this.stories = stories;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ComicList getComics() {
        return comics;
    }

    public void setComics(ComicList comics) {
        this.comics = comics;
    }

    public CharacterList getCharacters() {
        return characters;
    }

    public void setCharacter(CharacterList characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "endYear=" + endYear +
                ", previous=" + previous +
                ", events=" + events +
                ", urls=" + Arrays.toString(urls) +
                ", next=" + next +
                ", modified='" + modified + '\'' +
                ", creators=" + creators +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", thumbnail=" + thumbnail +
                ", stories=" + stories +
                ", resourceURI='" + resourceURI + '\'' +
                ", startYear=" + startYear +
                ", description='" + description + '\'' +
                ", rating='" + rating + '\'' +
                ", comics=" + comics +
                ", characters=" + characters +
                '}';
    }
}
