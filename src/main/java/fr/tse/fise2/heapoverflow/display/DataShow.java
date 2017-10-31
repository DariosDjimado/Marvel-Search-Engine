package fr.tse.fise2.heapoverflow.display;

import fr.tse.fise2.heapoverflow.marvelapi.Comic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

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
        this.setSize(1000, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel content = new JPanel();
        this.setLayout(new BorderLayout());

        Title1Panel head = new Title1Panel(comic.getTitle());
        head.setPreferredSize(new Dimension(head.getFontMetrics(Fonts.title1).stringWidth(comic.getTitle()), 45));
        this.getContentPane().add(head, BorderLayout.NORTH);

        ShowComicDetails detail = new ShowComicDetails(comic);
//        detail.setBackground(Color.RED);
        this.getContentPane().add(detail, BorderLayout.CENTER);

        try {
            URL url = new URL(comic.getThumbnail().getPath() + "/portrait_fantastic." + comic.getThumbnail().getExtension());
            System.out.println("getting " + url);
            BufferedImage image = ImageIO.read(url);
            ShowThumbnail thumb = new ShowThumbnail(image);
            thumb.setPreferredSize(new Dimension(200, 274));
            this.getContentPane().add(thumb, BorderLayout.WEST);
        }
        catch (Exception e){
            System.out.println(e);
        }


        this.setVisible(true);
    }
}
