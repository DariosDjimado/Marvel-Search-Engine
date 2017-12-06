package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.interfaces.UIComponent;
import javax.swing.*;
import java.awt.*;

public class UIBottomComponent implements UIComponent {
    private JPanel bottomWrapperPanel;
    private JProgressBar progressBar;

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public UIBottomComponent(JPanel bottomWrapperPanel) {
        this.bottomWrapperPanel = bottomWrapperPanel;
        this.progressBar = new JProgressBar();
    }

    public JPanel getBottomWrapperPanel() {
        return bottomWrapperPanel;
    }

    public void setBottomWrapperPanel(JPanel bottomWrapperPanel) {
        this.bottomWrapperPanel = bottomWrapperPanel;
    }

    @Override
    public void setSize() {



    }

    @Override
    public void build() {
        this.progressBar.setStringPainted(true);
        this.progressBar.setIndeterminate(true);
        this.progressBar.setSize(new Dimension(300,25));
        this.progressBar.setPreferredSize(new Dimension(300,25));

        this.bottomWrapperPanel.add(this.progressBar);

    }

    @Override
    public void setVisible() {

    }
}
