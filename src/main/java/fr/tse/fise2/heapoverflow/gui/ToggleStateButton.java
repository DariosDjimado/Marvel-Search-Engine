package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.authentication.User;
import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;
import fr.tse.fise2.heapoverflow.database.ElementsAssociation;
import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelElement;
import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;

import javax.swing.*;

/**
 * @author Darios DJIMADO
 */
public abstract class ToggleStateButton extends ButtonFormat {
    private final ImageIcon iconUnselected;
    private final ImageIcon iconSelected;
    protected int id;
    protected MarvelElement type;
    private boolean state;
    private String elementName;

    ToggleStateButton(ImageIcon iconSelected, ImageIcon iconUnselected) {
        this.iconUnselected = iconUnselected;
        this.iconSelected = iconSelected;
        this.id = -1;
    }

    public void setComic(Comic comic) {
        this.type = MarvelElement.COMIC;
        this.elementName = comic.getTitle();
        this.setId(comic.getId());
    }

    public void setCharacter(Character character) {
        this.type = MarvelElement.CHARACTER;
        this.elementName = character.getName();
        this.setId(character.getId());
    }

    public void setComic(Comic comic, ElementAssociationRow row) {
        this.type = MarvelElement.COMIC;
        this.elementName = comic.getTitle();
        this.id = comic.getId();

        if (row != null) {
            this.setState(updateState(row));
        } else {
            this.setState(false);
        }

    }

    public void setCharacter(Character character, ElementAssociationRow row) {
        this.type = MarvelElement.CHARACTER;
        this.elementName = character.getName();
        this.id = character.getId();

        if (row != null) {
            this.setState(updateState(row));
        } else {
            this.setState(false);
        }

    }

    void updateIcon(boolean reverse) {
        if (reverse) {
            if (this.isState()) {
                this.setIcon(iconUnselected);
            } else {
                this.setIcon(iconSelected);
            }
        } else {
            if (this.isState()) {
                this.setIcon(iconSelected);
            } else {
                this.setIcon(iconUnselected);
            }
        }
    }

    abstract void updateTooltipText();

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
        this.updateIcon(false);
        this.updateTooltipText();
    }

    protected abstract boolean updateState(ElementAssociationRow row);

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
        User user = UserAuthenticationModel.getUser();
        if (user != null) {
            ElementAssociationRow row = ElementsAssociation.findElement(user.getId(), this.id, this.type);
            if (row != null) {
                this.setState(updateState(row));
            } else {
                this.setState(false);
            }
        } else {
            this.setState(false);
        }
    }

    void refresh() {
        if (this.getId() != -1) {

            this.setId(this.getId());
        }
    }

    public MarvelElement getType() {
        return type;
    }

    public String getElementName() {
        return elementName;
    }
}
