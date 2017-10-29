package fr.tse.fise2.heapoverflow.display;

import fr.tse.fise2.heapoverflow.marvelapi.Comic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observer;

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
     * @param comic
     *      The comic object to display
     */
    public DataShow(Comic comic) {
        this.setTitle(comic.getTitle());
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        Title1Panel head = new Title1Panel(comic.getTitle());
        head.setLayout(new BoxLayout(head, BoxLayout.LINE_AXIS));
        this.getContentPane().add(head, BorderLayout.PAGE_START);

        this.setVisible(true);
    }
}
