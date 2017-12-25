package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.authentication.UserAuthentication;
import fr.tse.fise2.heapoverflow.database.ElementAssociationRow;
import fr.tse.fise2.heapoverflow.database.ElementsAssociation;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelElement;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public final class FavoriteButton extends ButtonFormat {
    private static final ImageIcon iconSelected = new ImageIcon(ComicsSearchListRenderer.class.getResource("favorite_add.png"));
    private static final ImageIcon iconUnselected = new ImageIcon(ComicsSearchListRenderer.class.getResource("favorite_remove.png"));
    private boolean state;
    private int id;
    private MarvelElement type;
    private String elementName;

    public FavoriteButton() {
        this.setIcon(iconUnselected);
        this.initConfig();
    }

    private void adaptIcon(boolean reverse) {
        if (reverse) {
            if (this.state) {
                this.setIcon(iconUnselected);
            } else {
                this.setIcon(iconSelected);
            }
        } else {
            if (this.state) {
                this.setIcon(iconSelected);
            } else {
                this.setIcon(iconUnselected);
            }
        }

    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
        if (this.state) {
            this.setToolTipText("Remove favorite");
            this.setIcon(iconSelected);
        } else {
            this.setToolTipText("Add favorite");
            this.setIcon(iconUnselected);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        if (UserAuthentication.isAuthenticated()) {
            ElementAssociationRow row = ElementsAssociation.findElement(
                    Objects.requireNonNull(UserAuthentication.getUser()).getId(),
                    this.id, this.type);
            if (row != null) {
                this.setState(row.isFavorite());
            } else {
                this.setState(false);
            }

        }
    }

    private void initConfig() {
        this.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                adaptIcon(true);
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseExited(MouseEvent e) {
                adaptIcon(false);
            }
        });
    }

    public MarvelElement getType() {
        return type;
    }

    public void setType(MarvelElement type) {
        this.type = type;
    }


}
