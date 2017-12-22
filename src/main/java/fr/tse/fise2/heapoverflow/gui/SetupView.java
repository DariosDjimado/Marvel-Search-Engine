package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.controllers.SetupController;
import fr.tse.fise2.heapoverflow.database.ConnectionDB;
import fr.tse.fise2.heapoverflow.models.SetupModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Observer;

/**
 * @author Darios DJIMADO
 */
public class SetupView implements Observer {
    private final JButton prevButton;
    private final JButton nextButton;
    private final JButton finishedButton;
    private final JFrame setupFrame;
    private final JPanel mainPanel;
    private final JPanel headerPanel;
    private final JRadioButton localConfig;
    private final JRadioButton remoteConfig;
    private final JList<String> logList;
    private final DefaultListModel<String> listModel;
    private final JProgressBar stateProgressBar;
    private final JPanel footerPanel;

    private final JPanel configPanel;
    private final JPanel installPanel;
    private final JPanel finishedPanel;

    private SetupModel model;
    private SetupController controller;

    private JPanel currentPagePanel;


    public SetupView(SetupModel model, SetupController controller) {
        this.model = model;
        this.controller = controller;
        this.setupFrame = new JFrame("setup");
        this.nextButton = new PrimaryButton("next");
        this.prevButton = new DefaultButton("prev");
        this.finishedButton = new PrimaryButton("Close ✔");
        this.headerPanel = new JPanel();
        this.mainPanel = new JPanel();
        this.configPanel = new JPanel();
        this.installPanel = new JPanel();
        this.finishedPanel = new JPanel();
        this.footerPanel = new JPanel();
        this.localConfig = new JRadioButton("local (recommended)");
        this.remoteConfig = new JRadioButton("online");
        this.listModel = new DefaultListModel<>();
        this.logList = new JList<>(listModel);
        this.stateProgressBar = new CustomProgressBar();
        this.init();
        this.configActionListener();
    }

    private void init() {
        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.setMinimumSize(new Dimension(300, 300));
        // header panel
        this.headerPanel.add(new JLabel("INSTALL MARVEL SEARCH V1.0.0"));
        this.mainPanel.add(this.headerPanel, BorderLayout.NORTH);

        // config panel
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(this.localConfig);
        buttonGroup.add(this.remoteConfig);
        this.remoteConfig.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
        this.localConfig.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
        this.localConfig.setSelected(true);
        this.configPanel.setLayout(new BoxLayout(this.configPanel, BoxLayout.Y_AXIS));
        this.configPanel.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
        this.configPanel.add(this.localConfig);
        this.configPanel.add(this.remoteConfig);
        this.mainPanel.add(this.configPanel, BorderLayout.CENTER);
        this.currentPagePanel = this.configPanel;

        //  install panel
        this.stateProgressBar.setIndeterminate(true);
        this.installPanel.setLayout(new BoxLayout(this.installPanel, BoxLayout.Y_AXIS));
        this.installPanel.add(this.stateProgressBar);
        this.installPanel.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
        this.installPanel.add(new JScrollPane(logList));

        // config done panel
        this.finishedPanel.add(new JLabel("Done"));


        // footer panel
        this.prevButton.setEnabled(false);
        this.footerPanel.setLayout(new BorderLayout());
        this.footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIColor.DEFAULT_COLOR));
        final JPanel footerButtonsPanel = new JPanel();
        footerButtonsPanel.add(this.prevButton);
        footerButtonsPanel.add(this.nextButton);
        this.footerPanel.add(footerButtonsPanel, BorderLayout.EAST);
        this.mainPanel.add(this.footerPanel, BorderLayout.PAGE_END);

        // frame setup
        this.setupFrame.setSize(400, 300);
        this.setupFrame.setResizable(false);
        this.setupFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        final Toolkit tk = Toolkit.getDefaultToolkit();
        this.setupFrame.add(mainPanel);
        final Dimension screenDimension = tk.getScreenSize();
        this.setupFrame.setLocation((int) screenDimension.getWidth() / 2 - this.setupFrame.getWidth() / 2,
                (int) screenDimension.getHeight() / 2 - this.setupFrame.getHeight() / 2);
        this.setupFrame.setVisible(true);
        this.setupFrame.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             *
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("closed");
                if (model.getCurrentPage() != SetupModel.FINISHED) {
                    if (JOptionPane.showConfirmDialog(setupFrame, "Do you want to quit the configuration",
                            "quit setup", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        model.cancelConfig();
                        try {
                            ConnectionDB.closeConnection();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }
            }
        });
    }


    private void gotoPage(int page) {
        this.mainPanel.remove(this.currentPagePanel);
        switch (page) {
            case SetupModel.SETUP: {
                this.mainPanel.add(this.configPanel, BorderLayout.CENTER);
                this.currentPagePanel = this.configPanel;
                this.prevButton.setEnabled(false);
                this.nextButton.setEnabled(true);
                break;
            }
            case SetupModel.INSTALL: {
                this.mainPanel.add(this.installPanel, BorderLayout.CENTER);
                this.currentPagePanel = this.installPanel;
                this.prevButton.setEnabled(false);
                this.nextButton.setEnabled(false);
                break;
            }
            case SetupModel.FINISHED: {
                this.mainPanel.add(this.finishedPanel, BorderLayout.CENTER);
                this.currentPagePanel = this.finishedPanel;
                this.footerPanel.removeAll();
                this.footerPanel.add(this.finishedButton, BorderLayout.EAST);
            }
            break;

            default:
                break;
        }
        this.setupFrame.repaint();
        this.setupFrame.revalidate();
    }

    public void setInstallFinished() {
        this.stateProgressBar.setIndeterminate(false);
        this.stateProgressBar.setValue(100);
    }

    private void configActionListener() {
        this.prevButton.addActionListener(e -> controller.prevPage());
        this.nextButton.addActionListener(e -> controller.nextPage());
        this.finishedButton.addActionListener(e -> System.exit(0));
    }

    public void onLog(String sentence) {
        EventQueue.invokeLater(() -> listModel.add(0, sentence));
    }

    public JRadioButton getRemoteConfig() {
        return remoteConfig;
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
    public void update(java.util.Observable o, Object arg) {
        System.out.println(arg);
        EventQueue.invokeLater(() -> {
            if (model == o) {
                gotoPage(model.getCurrentPage());
            }
        });
    }
}
