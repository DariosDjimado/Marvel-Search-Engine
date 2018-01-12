package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Character;

import java.util.Objects;

public class ChracterListElement implements MarvelListElement {

    private Character character;

    public ChracterListElement(Character character) {
        this.character = character;
    }

    @Override
    public Object getDispedO() {
        return character;
    }

    @Override
    public String toString() {
        return character.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChracterListElement that = (ChracterListElement) o;
        return Objects.equals(character, that.character);
    }

    @Override
    public int hashCode() {

        return Objects.hash(character);
    }
}
