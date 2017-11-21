package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class UI extends JFrame {
    private JPanel container;

    public UI() {
        super("Marvel Search");
    }

    public static void main(String[] args) {
        UI ui = new UI();

        ui.init();
    }

    private void init() {
        container = new JPanel();
        container.setLayout(new BorderLayout(0, 0));

        createMenu();
        createLeftWrapperPanel();
        createCenterWrapperPanel();
        createRightWrapperPanel();

        container.setVisible(true);
        System.out.println(container.isVisible());
        this.add(container);

        this.pack();
        this.setVisible(true);


    }

    public void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu aboutMenu = new JMenu("About");
        JMenuItem helpMenuItem = new JMenuItem("Help");
        aboutMenu.add(helpMenuItem);
        menuBar.add(aboutMenu);


        JMenu settingsJMenu = new JMenu("Settings");
        JMenuItem langMenuItem = new JMenuItem("Language");
        settingsJMenu.add(langMenuItem);
        menuBar.add(settingsJMenu);


        this.setJMenuBar(menuBar);
    }

    public void createLeftWrapperPanel() {
        JPanel leftWrapperPanel = new JPanel();
        leftWrapperPanel.setLayout(new BorderLayout(0, 0));
        leftWrapperPanel.setMinimumSize(new Dimension(300, 500));
        leftWrapperPanel.setPreferredSize(new Dimension(300, 500));

        UISearchComponent uiSearchComponent = new UISearchComponent();
        uiSearchComponent.setup(leftWrapperPanel);


        container.add(leftWrapperPanel, BorderLayout.WEST);


    }

    public void createCenterWrapperPanel() {
        JPanel centerWrapperPanel = new JPanel();
        centerWrapperPanel.setLayout(new GridBagLayout());
        centerWrapperPanel.setMinimumSize(new Dimension(300, 500));
        centerWrapperPanel.setPreferredSize(new Dimension(300, 500));
        centerWrapperPanel.setVisible(true);
        centerWrapperPanel.setBorder(BorderFactory.createMatteBorder(0,1,0,1,Color.BLUE));

        container.add(centerWrapperPanel, BorderLayout.CENTER);




    }

    public void createRightWrapperPanel() {
        JPanel rightWrapperPanel = new JPanel();
        rightWrapperPanel.setLayout(new GridBagLayout());
        rightWrapperPanel.setMinimumSize(new Dimension(300, 500));
        rightWrapperPanel.setPreferredSize(new Dimension(300, 500));

        container.add(rightWrapperPanel, BorderLayout.EAST);
    }


}



