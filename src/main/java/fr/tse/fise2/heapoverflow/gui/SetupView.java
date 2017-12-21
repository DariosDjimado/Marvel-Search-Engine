package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.controllers.SetupController;
import fr.tse.fise2.heapoverflow.models.SetupModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observer;

public class SetupView implements Observer {
    private final JButton prevButton;
    private final JButton nextButton;
    private final JFrame setupFrame;
    private final JPanel mainPanel;
    private final JPanel headerPanel;
    private final JRadioButton localConfig;
    private final JRadioButton remoteConfig;
    private final JList<String> logList;
    private final DefaultListModel<String> listModel;
    private final JProgressBar stateProgressBar;

    private final JPanel configPanel;
    private final JPanel installPanel;

    private SetupModel model;
    private SetupController controller;


    public SetupView(SetupModel model, SetupController controller) {
        this.model = model;
        this.controller = controller;
        this.setupFrame = new JFrame("setup");
        this.nextButton = new PrimaryButton("next");
        this.prevButton = new DefaultButton("prev");
        this.mainPanel = new JPanel();
        this.configPanel = new JPanel();
        this.installPanel = new JPanel();
        this.localConfig = new JRadioButton("local (recommended)");
        this.remoteConfig = new JRadioButton("online");
        this.headerPanel = new JPanel();
        this.listModel = new DefaultListModel<>();
        this.logList = new JList<>(listModel);
        this.stateProgressBar = new JProgressBar();
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

        //  install panel
        this.stateProgressBar.setIndeterminate(false);
        this.stateProgressBar.setValue(90);
        this.stateProgressBar.setBackground(UIColor.PRIMARY_COLOR);
        this.installPanel.setLayout(new BoxLayout(this.installPanel, BoxLayout.Y_AXIS));
        this.installPanel.add(this.stateProgressBar);
        this.installPanel.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
        this.installPanel.add(new JScrollPane(logList));


        // footer panel
        this.prevButton.setEnabled(false);
        JPanel footerPanel = new JPanel();
        footerPanel.add(this.prevButton);
        footerPanel.add(this.nextButton);
        this.mainPanel.add(footerPanel, BorderLayout.PAGE_END);

        // frame setup
        this.setupFrame.setSize(400, 300);
        this.setupFrame.setResizable(false);
        this.setupFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setupFrame.add(mainPanel);
        Dimension screenDimension = tk.getScreenSize();
        this.setupFrame.setLocation((int) screenDimension.getWidth() / 2 - this.setupFrame.getWidth() / 2,
                (int) screenDimension.getHeight() / 2 - this.setupFrame.getHeight() / 2);
        this.setupFrame.setVisible(true);
    }


    private void gotoPage(int page) {
        if (page == 1) {
            this.mainPanel.remove(this.configPanel);
            this.mainPanel.add(this.installPanel, BorderLayout.CENTER);
            this.prevButton.setEnabled(true);
            this.nextButton.setEnabled(false);

        } else {
            this.mainPanel.remove(this.installPanel);
            this.mainPanel.add(this.configPanel, BorderLayout.CENTER);
            this.prevButton.setEnabled(false);
            this.nextButton.setEnabled(true);
        }

        this.setupFrame.repaint();
        this.setupFrame.revalidate();
    }

    private void configActionListener() {
        this.prevButton.addActionListener(e -> controller.prevPage());
        this.nextButton.addActionListener(e -> controller.nextPage());
    }

    public void onLog(String sentence) {
        EventQueue.invokeLater(() -> listModel.addElement(sentence));
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
