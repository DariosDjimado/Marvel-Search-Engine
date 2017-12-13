package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UILibrary {
    JPanel panel;

    JTabbedPane tabs;

    JPanel libTable;

    List<JPanel> libLines;

    JPanel favTable;

    List<JPanel> favLines;

    public UILibrary(JPanel panel) {
        this.panel = panel;
        this.panel.setLayout(new BorderLayout());

        tabs = new JTabbedPane();
        this.panel.add(tabs);

        libTable = new JPanel();
        libTable.setLayout(new BoxLayout(libTable, BoxLayout.PAGE_AXIS));
        tabs.add("Library", libTable);

        favTable = new JPanel();
        favTable.setLayout(new BoxLayout(favTable, BoxLayout.PAGE_AXIS));
        tabs.add("Favorites", favTable);

        refreshLib();
        refreshFav();
        this.panel.setVisible(true);
    }

    public void refreshLib(){
        JPanel line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.LINE_AXIS));
        line.add(new JLabel("Iron man"));
        line.add(new JButton("Favorite"));
        line.add(new JButton("Read"));
        line.add(new JButton("Remove"));
        libTable.add(line);

        line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.LINE_AXIS));
        line.add(new JLabel("Captain America Civil War"));
        line.add(new JButton("Favorite"));
        line.add(new JButton("Read"));
        line.add(new JButton("Remove"));
        libTable.add(line);
    }

    public void refreshFav(){
        JPanel line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.LINE_AXIS));
        line.add(new JLabel("Iron man"));
        line.add(new JButton("Remove"));
        favTable.add(line);

        line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.LINE_AXIS));
        line.add(new JLabel("Captain America Civil War"));
        line.add(new JButton("Remove"));
        favTable.add(line);
    }
}
