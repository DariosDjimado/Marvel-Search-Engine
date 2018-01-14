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
    /** Wrapper Panel used
     */
    private JPanel bottomWrapperPanel;

    /**     JProgressBar is an already existing class in swing*
     */
    private JProgressBar progressBar;

    /**     errorIcon displayed for errors
     */
    private JOptionPane errorIcon;

    /**     displaying the url now searched
     */
    private JLabel urlLabel;

    /**     Constructor
     */
    public UIBottomComponent(JPanel bottomWrapperPanel) {
        this.bottomWrapperPanel = bottomWrapperPanel;
        this.progressBar = new CustomProgressBar();
        this.errorIcon = new JOptionPane();
        this.progressBar.setVisible(false);

        this.urlLabel = new JLabel("default");
    }

    /**     Getters/Setters
     */
    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public JPanel getBottomWrapperPanel() {
        return bottomWrapperPanel;
    }

    public void setBottomWrapperPanel(JPanel bottomWrapperPanel) {
        this.bottomWrapperPanel = bottomWrapperPanel;
    }


    public JOptionPane getErrorIcon() {
        return errorIcon;
    }

    public void setErrorIcon(JOptionPane errorIcon) {
        this.errorIcon = errorIcon;
    }

    public void displayErrorPopup(String message) {
        JOptionPane.showMessageDialog(null, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public JLabel getUrlLabel() {
        return urlLabel;
    }

    public void setUrlLabel(JLabel urlLabel) {
        this.urlLabel = urlLabel;
    }

    public void build() {
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
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(attributionLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        )

        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(this.urlLabel)
                                .addComponent(this.progressBar)
                                .addComponent(attributionLabel))
        );

        groupLayout.linkSize(SwingConstants.HORIZONTAL, this.urlLabel, this.progressBar);
        groupLayout.minimumLayoutSize(this.bottomWrapperPanel);


    }

}
