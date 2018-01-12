package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Story;

import java.util.Objects;

public class StoryListElement implements MarvelListElement {

    private Story story;

    public StoryListElement(Story story) {
        this.story = story;
    }

    @Override
    public Object getDispedO() {
        return story;
    }

    @Override
    public String toString() {
        return story.getTitle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryListElement that = (StoryListElement) o;
        return Objects.equals(story, that.story);
    }

    @Override
    public int hashCode() {

        return Objects.hash(story);
    }
}
