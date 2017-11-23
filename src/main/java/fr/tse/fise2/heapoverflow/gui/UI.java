package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import java.awt.*;


public class UI extends JFrame {
    private JPanel container;
    private UISearchComponent uiSearchComponent;
    private JPanel centerWrapperPanel;

    public UI() {
        super("Marvel Search");
        this.init();
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
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

        this.uiSearchComponent = new UISearchComponent(leftWrapperPanel);
        uiSearchComponent.setup();


        container.add(leftWrapperPanel, BorderLayout.WEST);


    }

    public void createCenterWrapperPanel() {
        this.centerWrapperPanel = new JPanel();
        centerWrapperPanel.setLayout(new GridBagLayout());
        centerWrapperPanel.setMinimumSize(new Dimension(600, 500));
        centerWrapperPanel.setPreferredSize(new Dimension(600, 500));
        centerWrapperPanel.setVisible(true);
        centerWrapperPanel.setBorder(BorderFactory.createMatteBorder(0,1,0,1,Color.gray));


       // DataShow.DrawEmpty(centerWrapperPanel);



        container.add(centerWrapperPanel, BorderLayout.CENTER);




    }

    public void createRightWrapperPanel() {
        JPanel rightWrapperPanel = new JPanel();
        rightWrapperPanel.setLayout(new GridBagLayout());
        rightWrapperPanel.setMinimumSize(new Dimension(300, 500));
        rightWrapperPanel.setPreferredSize(new Dimension(300, 500));

        container.add(rightWrapperPanel, BorderLayout.EAST);
    }

    public UISearchComponent getUiSearchComponent() {
        return uiSearchComponent;
    }

    public JPanel getCenterWrapperPanel() {
        return centerWrapperPanel;
    }
}



