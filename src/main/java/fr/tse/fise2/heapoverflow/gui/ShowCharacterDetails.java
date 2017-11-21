package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Character;
import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.ComicPrice;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel to show detailed informations on Characters
 */
public class ShowCharacterDetails extends JPanel {
    /**
     * Character which informations are displayed
     */
    Character character_;

    /**
     * Constructor of the panel
     * @param character_
     *      Character which will be displayed
     */
    public ShowCharacterDetails(Character character_) {
        this.character_ = character_;
        

    }

    /**
     * Method called to draw the JPanel
     * @param g
     *      Graphics object of the JPanel
     */
    @Override
    public void paintComponent(Graphics g) {
        int lineCount = 0;
        //Apearances
        lineCount++;
        g.setFont(Fonts.boldContent);
        g.drawString("Appears in : ", 5, g.getFont().getSize()*lineCount);
        g.setFont(Fonts.content);
        lineCount++;
        g.drawString(" - " + Integer.valueOf(character_.getSeries().getAvailable()).toString() + " Series", 5 , g.getFont().getSize()*lineCount);
        lineCount++;
        g.drawString(" - " + Integer.valueOf(character_.getComics().getAvailable()).toString() + " Comics", 5 , g.getFont().getSize()*lineCount);

        //Last Update
        lineCount++;
        lineCount++;
        g.setFont(Fonts.boldContent);
        g.drawString("Last modification : ", 5, g.getFont().getSize()*lineCount);
        g.setFont(Fonts.content);
        g.drawString(character_.getModified().substring(0, 10), 5 + g.getFontMetrics(Fonts.boldContent).stringWidth("Last modification : "), g.getFont().getSize()*lineCount);

    }
}
