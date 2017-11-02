package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.marvelapi.Comic;
import fr.tse.fise2.heapoverflow.marvelapi.ComicPrice;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel to show detailed informations on Comics
 */
public class ShowComicDetails extends JPanel {
    /**
     * Comic which informations are displayed
     */
    Comic comic_;

    /**
     * Constructor of the panel
     * @param comic_
     *      Comic which will be displayed
     */
    public ShowComicDetails(Comic comic_) {
        this.comic_ = comic_;
    }

    /**
     * Method called to draw the JPanel
     * @param g
     *      Graphics object of the JPanel
     */
    @Override
    public void paintComponent(Graphics g) {
        int lineCount = 0;
        //Format
        lineCount++;
        g.setFont(Fonts.boldContent);
        g.drawString("Format : ", 5, g.getFont().getSize()*lineCount);
        g.setFont(Fonts.content);
        g.drawString(comic_.getFormat(), 5 + g.getFontMetrics(Fonts.boldContent).stringWidth("Format : "), g.getFont().getSize()*lineCount);

        //Serie
        lineCount++;
        g.setFont(Fonts.boldContent);
        g.drawString("Serie : ", 5, g.getFont().getSize()*lineCount);
        g.setFont(Fonts.content);
        g.drawString(comic_.getSeries().getName(), 5 + g.getFontMetrics(Fonts.boldContent).stringWidth("Serie : "), g.getFont().getSize()*lineCount);

        //issueNumber (if != 0)
        lineCount++;
        if (comic_.getIssueNumber() > 0) {
            g.setFont(Fonts.boldContent);
            g.drawString("Issue Number : ", 5, g.getFont().getSize() * lineCount);
            g.setFont(Fonts.content);
            g.drawString(comic_.getIssueNumber().toString(), 5 + g.getFontMetrics(Fonts.boldContent).stringWidth("Issue Number : "), g.getFont().getSize() * lineCount);
        }

        //Price
        lineCount++;
        g.setFont(Fonts.boldContent);
        g.drawString("Prices : ", 5, g.getFont().getSize()*lineCount);
        g.setFont(Fonts.content);
        for (ComicPrice price : comic_.getPrices()) {
            lineCount++;
            g.drawString("- " + price.getType() + " : " + price.getPrice(), 15, g.getFont().getSize() * lineCount);
        }



        //pageCount
        lineCount++;
        if (comic_.getIssueNumber() > 0) {
            g.setFont(Fonts.boldContent);
            g.drawString("Page Count : ", 5, g.getFont().getSize() * lineCount);
            g.setFont(Fonts.content);
            g.drawString(String.valueOf(comic_.getPageCount()), 5 + g.getFontMetrics(Fonts.boldContent).stringWidth("Page Count : "), g.getFont().getSize() * lineCount);
        }

    }
}
