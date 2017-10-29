package fr.tse.fise2.heapoverflow.display;

import javax.swing.*;
import java.awt.*;

/**
 * DataShow is the classe used to display detailed datas on characters, comics ...
 * (still a stub at this stage)
 * can display :
 * <ul>
 *     <li>Comics</li>
 * </ul>
 *
 * @author Théo Basty
 * @version 0.1
 */
public class DataShow extends JFrame {
    /**
     * Constructor to display comics
     */
    public DataShow(String comicName) {
        this.setTitle(comicName);
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Instanciation d'un objet JPanel
        ComicsPanel pan = new ComicsPanel(comicName);
        //On prévient notre JFrame que notre JPanel sera son content pane
        this.setContentPane(pan);
        this.setVisible(true);
    }
}

/**
 * Extending JPanel to adapt for comics displaying.
 * Light example to test swing
 * @author Théo Basty
 * @version 0.1
 */
class ComicsPanel extends JPanel{
    /**
     * Name of the comic displayed
     */
    String comicName_;

    /**
     * Class constructor
     * @param comicName
     *      Name of the comic to display
     */
    public ComicsPanel(String comicName) {
        comicName_ = comicName;
    }

    /**
     * function called to redraw window content
     * @param g
     *      Graphics object from AWT
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setFont(Fonts.title1);
        g.drawString(comicName_, 10,40);
    }
}
