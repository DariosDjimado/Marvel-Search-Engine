package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.authentication.UserAuthentication;
import fr.tse.fise2.heapoverflow.database.FavoriteRow;
import fr.tse.fise2.heapoverflow.database.FavoritesTable;
import fr.tse.fise2.heapoverflow.marvelapi.MarvelElements;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Objects;

public class FavoriteButton extends JButton {
    private boolean state;
    private int id;
    private MarvelElements type;
    private String elementName;

    /**
     * Creates a button with text.
     */
    public FavoriteButton() {
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setContentAreaFilled(true);
        this.setForeground(UIColor.MAIN_BACKGROUND_COLOR);
        this.setBackground(UIColor.PRIMARY_COLOR);
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
        if (!this.state) {
            this.setText("+Favorite");
        } else {
            this.setText("-Favorite");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        if (UserAuthentication.isAuthenticated()) {
            try {
                FavoriteRow favoriteRow = FavoritesTable
                        .existsFavorite(
                                Objects.requireNonNull(UserAuthentication.getUser()).getId(),
                                this.id, this.type.getValue());
                if (favoriteRow != null) {
                    this.setState(favoriteRow.isFavorite());
                } else {
                    this.setState(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public MarvelElements getType() {
        return type;
    }

    public void setType(MarvelElements type) {
        this.type = type;
    }
}
