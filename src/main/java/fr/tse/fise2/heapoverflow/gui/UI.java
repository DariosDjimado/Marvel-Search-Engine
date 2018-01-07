package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import java.awt.*;


public class UI extends JFrame {
    private final JPanel topPanel;
    private final UITopComponent uiTopComponent;
    private final JPanel searchViewPanel;
    private final JPanel libraryViewPanel;
    private final JTabbedPane tabbedPane;
    private JPanel container;
    private UISearchComponent uiSearchComponent;
    private UIExtraComponent uiExtraComponent;
    private JPanel centerWrapperPanel;
    private UIBottomComponent uiBottomComponent;
    private UILibrary uiLibrary;


    public UI() {
        super("Marvel Search");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.topPanel = new JPanel();

        this.searchViewPanel = new JPanel(new BorderLayout());
        this.libraryViewPanel = new JPanel();

        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setBackground(UIColor.MAIN_BACKGROUND_COLOR);

        uiTopComponent = new UITopComponent(this, this.topPanel);

    }

    public void init() {

        container = new JPanel();
        container.setLayout(new BorderLayout(0, 0));
        this.uiLibrary = new UILibrary(libraryViewPanel);

        this.tabbedPane.add("Search ", this.searchViewPanel);
        this.tabbedPane.add("Library", this.libraryViewPanel);
        this.tabbedPane.add("Favorite", new FavoriteView());
        this.tabbedPane.add("Collection", CollectionsView.getInstance());

        container.add(tabbedPane, BorderLayout.CENTER);


        createMenu();
        configureTopPanel();
        createLeftWrapperPanel();
        createCenterWrapperPanel();
        createRightWrapperPanel();
        createBottomWrapperPanel();

        container.setVisible(true);
        this.add(container);

        this.pack();
        this.setVisible(false);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(UIColor.HEADER_SHADOW_COLOR);

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

    private void configureTopPanel() {


        this.container.add(this.topPanel, BorderLayout.NORTH);
        uiTopComponent.init();
    }


    private void createLeftWrapperPanel() {
        JPanel leftWrapperPanel = new JPanel();
        leftWrapperPanel.setLayout(new BorderLayout(0, 0));
        leftWrapperPanel.setMinimumSize(new Dimension(300, 500));
        leftWrapperPanel.setPreferredSize(new Dimension(300, 500));

        this.uiSearchComponent = new UISearchComponent(leftWrapperPanel);
        uiSearchComponent.setup();


        container.add(leftWrapperPanel, BorderLayout.WEST);


    }

    private void createCenterWrapperPanel() {
        this.centerWrapperPanel = new JPanel();
        centerWrapperPanel.setLayout(new GridBagLayout());
        centerWrapperPanel.setMinimumSize(new Dimension(600, 500));
        centerWrapperPanel.setPreferredSize(new Dimension(600, 500));
        centerWrapperPanel.setVisible(true);
        //centerWrapperPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.gray));
        centerWrapperPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
        centerWrapperPanel.setBackground(UIColor.MAIN_BACKGROUND_COLOR);

        //  container.add(centerWrapperPanel, BorderLayout.CENTER);

        this.searchViewPanel.add(this.centerWrapperPanel, BorderLayout.CENTER);

    }

    private void createRightWrapperPanel() {
        JPanel rightWrapperPanel = new JPanel();
        rightWrapperPanel.setMinimumSize(new Dimension(320, 500));
        rightWrapperPanel.setPreferredSize(new Dimension(320, 500));

        this.uiExtraComponent = new UIExtraComponent(rightWrapperPanel);
        this.uiExtraComponent.build();


        // container.add(rightWrapperPanel, BorderLayout.EAST);
        this.searchViewPanel.add(rightWrapperPanel, BorderLayout.EAST);
    }

    private void createBottomWrapperPanel() {
        JPanel bottomWrapperPanel = new JPanel();
        this.uiBottomComponent = new UIBottomComponent(bottomWrapperPanel);
        this.uiBottomComponent.build();

        container.add(bottomWrapperPanel, BorderLayout.SOUTH);


    }

    public UISearchComponent getUiSearchComponent() {
        return uiSearchComponent;
    }

    public JPanel getCenterWrapperPanel() {
        return centerWrapperPanel;
    }

    public UIExtraComponent getUiExtraComponent() {
        return uiExtraComponent;
    }

    public UIBottomComponent getUiBottomComponent() {
        return uiBottomComponent;
    }

    public UITopComponent getUiTopComponent() {
        return uiTopComponent;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}


