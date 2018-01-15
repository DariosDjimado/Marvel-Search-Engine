package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Here is the bottom component, in which are placed pieces of information
 * about requests, queries, and the progress bar.
 *
 * @author Lionel Rajaona
 */
public class UIBottomComponent {
    /**
     * errorIcon displayed for errors
     */
    private static final ImageIcon errorIcon = new ImageIcon(UIBottomComponent.class.getResource("error_icon.png"));
    /**
     * error button
     */
    private final JButton viewErrorButton;
    /**
     * error dialog
     */
    private final CustomDialog errorDialog;
    /**
     * error list model
     */
    private final DefaultListModel<String> errorListModel;
    /**
     * error list
     */
    private final JList<String> errorList;
    /**
     * Wrapper Panel used
     */
    private JPanel bottomWrapperPanel;
    /**
     * JProgressBar is an already existing class in swing*
     */
    private JProgressBar progressBar;
    /**
     * displaying the url now searched
     */
    private JLabel urlLabel;

    /**
     * Constructor
     */
    UIBottomComponent(JPanel bottomWrapperPanel) {
        this.bottomWrapperPanel = bottomWrapperPanel;
        this.progressBar = new CustomProgressBar();
        this.progressBar.setVisible(false);

        this.urlLabel = new JLabel("default");
        this.viewErrorButton = new DecorateButtonFormat(errorIcon);
        this.errorDialog = new CustomDialog(null, "errors occurred during this session");

        this.errorListModel = new DefaultListModel<>();
        this.errorList = new JList<>(this.errorListModel);
    }

    /**
     * Getters/Setters
     */
    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JLabel getUrlLabel() {
        return urlLabel;
    }

    public void build() {
        // error dialog
        JPanel errorDialogContentPane = (JPanel) this.errorDialog.getContentPane();
        errorDialogContentPane.setLayout(new BorderLayout());
        errorDialogContentPane.add(new CustomScrollPane(this.errorList));
        // error list
        this.errorList.setForeground(UIColor.ACCENT_COLOR);
        // error icon
        this.viewErrorButton.setOpaque(false);
        this.viewErrorButton.setVisible(false);
        this.viewErrorButton.setToolTipText("vie errors");

        this.progressBar.setIndeterminate(true);
        this.progressBar.setPreferredSize(new Dimension(300, 3));
        this.progressBar.setMaximumSize(new Dimension(300, 3));
        this.progressBar.setMinimumSize(new Dimension(300, 3));

        this.progressBar.setBackground(UIColor.MAIN_BACKGROUND_COLOR);


        //this.bottomWrapperPanel.add(this.errorIcon);
        this.urlLabel.setSize(new Dimension(300, 25));
        this.urlLabel.setPreferredSize(new Dimension(300, 25));

        JLabel attributionLabel = new JLabel("<HTML><FONT color=\"#000099\"><U>Data provided by Marvel. © 2017 MARVEL</U></FONT></HTML>");
        attributionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));


        this.bottomWrapperPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, UIColor.HEADER_SHADOW_COLOR));

        GroupLayout groupLayout = new GroupLayout(this.bottomWrapperPanel);
        this.bottomWrapperPanel.setLayout(groupLayout);

        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(this.urlLabel)
                        .addComponent(this.progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(this.viewErrorButton)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(attributionLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        )

        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(this.urlLabel)
                                .addComponent(this.progressBar)
                                .addComponent(this.viewErrorButton)
                                .addComponent(attributionLabel))
        );

        groupLayout.linkSize(SwingConstants.HORIZONTAL, this.urlLabel, this.progressBar);
        groupLayout.minimumLayoutSize(this.bottomWrapperPanel);


        this.viewErrorButton.addActionListener(e -> this.errorDialog.customSetVisible());
    }

    public CustomDialog getErrorDialog() {
        return errorDialog;
    }

    public void addNewError(Exception e) {
        this.errorListModel.addElement(e.getMessage());
        this.viewErrorButton.setVisible(true);
    }
}
