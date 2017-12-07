package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.interfaces.UIComponent;
import javax.swing.*;
import java.awt.*;

/**Here is the bottom component, in which are placed pieces of information
 * about requests, queries, and the progress bar.
 * @author Lionel Rajaona
 */
public class UIBottomComponent implements UIComponent {
    //Wrapper Panel used
    private JPanel bottomWrapperPanel;

    //JProgressBar is an already existing class in swing
    private JProgressBar progressBar;

    private JOptionPane errorIcon;

    //Constructor
    public UIBottomComponent(JPanel bottomWrapperPanel) {
        this.bottomWrapperPanel = bottomWrapperPanel;
        this.progressBar = new JProgressBar();
        this.errorIcon = new JOptionPane();
        this.progressBar.setVisible(false);
    }

    //Getters/Setters
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

    public void displayErrorPopup(String message){
        this.errorIcon.showMessageDialog(null,message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    //Override methods existing from UIComponent

    @Override
    public void setSize() {



    }


    @Override
    public void build() {
        this.progressBar.setIndeterminate(true);
        this.progressBar.setSize(new Dimension(300,25));
        this.progressBar.setPreferredSize(new Dimension(300,25));
        this.bottomWrapperPanel.add(this.progressBar);
        //this.bottomWrapperPanel.add(this.errorIcon);

    }

    @Override
    public void setVisible() {

    }
}
