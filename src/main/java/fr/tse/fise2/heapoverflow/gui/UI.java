package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.models.UserAuthenticationModel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Main Frame
 *
 * @author Darios DJIMADO
 * @author Ismail BERRADA
 */
public class UI extends JFrame implements Observer {
    private final JPanel topPanel;
    private final UITopComponent uiTopComponent;
    private final JPanel searchViewPanel;
    private final JTabbedPane tabbedPane;
    private final FavoriteView favoriteView;
    private final LibraryView libraryView;
    private final RecommendView recommendView;
    private final SplashScreen splashScreen;
    private JPanel container;
    private UISearchComponent uiSearchComponent;
    private UIExtraComponent uiExtraComponent;
    private JPanel centerWrapperPanel;
    private UIBottomComponent uiBottomComponent;

    public UI() {
        super("Marvel Search");
        this.splashScreen = SplashScreen.getSplashScreen();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.topPanel = new JPanel();
        this.searchViewPanel = new JPanel(new BorderLayout());
        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setUI(new BasicTabbedPaneUI());
        this.tabbedPane.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
        this.favoriteView = new FavoriteView();
        this.libraryView = new LibraryView();
        this.recommendView = new RecommendView();
        this.uiTopComponent = new UITopComponent(this, this.topPanel);
        UserAuthenticationModel.getInstance().addObserver(this);

    }

    /**
     * Initializes the frame
     */
    public void init() {
        container = new JPanel();
        container.setLayout(new BorderLayout(0, 0));

        this.tabbedPane.add("Search ", this.searchViewPanel);
        this.tabbedPane.add("Recommendations", this.recommendView);
        this.tabbedPane.add("Library", this.libraryView);
        this.tabbedPane.add("Favorite", this.favoriteView);
        this.tabbedPane.add("Collection", CollectionsView.getInstance());

        // disable, library, favorite and collection panel
        this.tabbedPane.setEnabledAt(2, false);
        this.tabbedPane.setEnabledAt(3, false);
        this.tabbedPane.setEnabledAt(4, false);

        //
        this.tabbedPane.addChangeListener(e -> {
            if (this.tabbedPane.getSelectedComponent() == this.favoriteView || this.tabbedPane.getSelectedComponent() == this.libraryView) {
                ((FavoriteView) this.tabbedPane.getSelectedComponent()).refresh();
            }
            if (this.tabbedPane.getSelectedComponent() == this.recommendView) {
                ((RecommendView) this.tabbedPane.getSelectedComponent()).refresh();
            }
        });

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

    /**
     * Creates the main menu
     */
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

    /**
     * Initializes the top wrapper
     */
    private void configureTopPanel() {
        this.container.add(this.topPanel, BorderLayout.NORTH);
        uiTopComponent.init();
    }

    /**
     * Initializes the left wrapper
     */
    private void createLeftWrapperPanel() {
        JPanel leftWrapperPanel = new JPanel();
        leftWrapperPanel.setLayout(new BorderLayout(0, 0));
        leftWrapperPanel.setMinimumSize(new Dimension(300, 500));
        leftWrapperPanel.setPreferredSize(new Dimension(300, 500));
        this.uiSearchComponent = new UISearchComponent(leftWrapperPanel);
        uiSearchComponent.setup();
        container.add(leftWrapperPanel, BorderLayout.WEST);
    }

    /**
     * Initializes the main wrapper
     */
    private void createCenterWrapperPanel() {
        this.centerWrapperPanel = new JPanel();
        centerWrapperPanel.setLayout(new GridBagLayout());
        centerWrapperPanel.setMinimumSize(new Dimension(600, 500));
        centerWrapperPanel.setPreferredSize(new Dimension(600, 500));
        centerWrapperPanel.setVisible(true);
        centerWrapperPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
        centerWrapperPanel.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
        this.searchViewPanel.add(this.centerWrapperPanel, BorderLayout.CENTER);

    }

    /**
     * Initializes the right wrapper
     */
    private void createRightWrapperPanel() {
        JPanel rightWrapperPanel = new JPanel();
        rightWrapperPanel.setMinimumSize(new Dimension(320, 500));
        rightWrapperPanel.setPreferredSize(new Dimension(320, 500));

        this.uiExtraComponent = new UIExtraComponent(rightWrapperPanel);
        this.searchViewPanel.add(rightWrapperPanel, BorderLayout.EAST);
    }

    /**
     * Initializes the bottom wrapper
     */
    private void createBottomWrapperPanel() {
        JPanel bottomWrapperPanel = new JPanel();
        this.uiBottomComponent = new UIBottomComponent(bottomWrapperPanel);
        this.uiBottomComponent.build();
        this.container.add(bottomWrapperPanel, BorderLayout.SOUTH);
        this.uiBottomComponent.getErrorDialog().setLocationRelativeTo(this);
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

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public RecommendView getRecommendView() {
        return recommendView;
    }

    /**
     * Displays the frame
     */
    public void display() {
        if (this.splashScreen != null) {
            this.splashScreen.close();
        }
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        this.setLocation((int) toolkit.getScreenSize().getWidth() / 2 - this.getWidth() / 2,
                (int) toolkit.getScreenSize().getHeight() / 2 - this.getHeight() / 2);
        this.pack();
        this.setVisible(true);
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o == UserAuthenticationModel.getInstance()) {
            if (arg != null) {
                // enable, library, favorite and collection panel
                this.tabbedPane.setEnabledAt(2, true);
                this.tabbedPane.setEnabledAt(3, true);
                this.tabbedPane.setEnabledAt(4, true);
                this.tabbedPane.setSelectedIndex(2);
            } else {
                // disable, library, favorite and collection panel
                this.tabbedPane.setEnabledAt(2, false);
                this.tabbedPane.setEnabledAt(3, false);
                this.tabbedPane.setEnabledAt(4, false);
                this.tabbedPane.setSelectedIndex(0);
            }
        }
    }
}


