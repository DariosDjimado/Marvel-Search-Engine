package fr.tse.fise2.heapoverflow.marvelapi;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Event POJO
 *
 * @author Darios DJIMADO
 */
public class Event implements Comparable<Event> {
    private int id;
    private String title;
    private String description;
    private String resourceURI;
    private Url urls[];
    private String modified;
    private String start;
    private String end;
    private Image thumbnail;
    private ComicList comics;
    private StoryList stories;
    private SeriesList series;
    private CharacterList characters;
    private CreatorList creators;
    private EventSummary next;
    private EventSummary previous;

    public int getId() {
        return id;
    }

    public Event setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Event setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Event setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public Event setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
        return this;
    }

    public Url[] getUrls() {
        return urls;
    }

    public Event setUrls(Url[] urls) {
        this.urls = urls;
        return this;
    }

    public String getModified() {
        return modified;
    }

    public Event setModified(String modified) {
        this.modified = modified;
        return this;
    }

    public String getStart() {
        return start;
    }

    public Event setStart(String start) {
        this.start = start;
        return this;
    }

    public String getEnd() {
        return end;
    }

    public Event setEnd(String end) {
        this.end = end;
        return this;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public Event setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public ComicList getComics() {
        return comics;
    }

    public Event setComics(ComicList comics) {
        this.comics = comics;
        return this;
    }

    public StoryList getStories() {
        return stories;
    }

    public Event setStories(StoryList stories) {
        this.stories = stories;
        return this;
    }

    public SeriesList getSeries() {
        return series;
    }

    public Event setSeries(SeriesList series) {
        this.series = series;
        return this;
    }

    public CharacterList getCharacters() {
        return characters;
    }

    public Event setCharacters(CharacterList characters) {
        this.characters = characters;
        return this;
    }

    public CreatorList getCreators() {
        return creators;
    }

    public Event setCreators(CreatorList creators) {
        this.creators = creators;
        return this;
    }

    public EventSummary getNext() {
        return next;
    }

    public Event setNext(EventSummary next) {
        this.next = next;
        return this;
    }

    public EventSummary getPrevious() {
        return previous;
    }

    public Event setPrevious(EventSummary previous) {
        this.previous = previous;
        return this;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", resourceURI='" + resourceURI + '\'' +
                ", urls=" + Arrays.toString(urls) +
                ", modified='" + modified + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", thumbnail=" + thumbnail +
                ", comics=" + comics +
                ", stories=" + stories +
                ", series=" + series +
                ", characters=" + characters +
                ", creators=" + creators +
                ", next=" + next +
                ", previous=" + previous +
                '}';
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */

    @Override
    public int compareTo(@Nullable Event o) {
        if ((o == null) || (this.getClass() != o.getClass())) {
            return 0;
        } else {
            return this.getTitle().compareTo(o.getTitle());
        }
    }
}
