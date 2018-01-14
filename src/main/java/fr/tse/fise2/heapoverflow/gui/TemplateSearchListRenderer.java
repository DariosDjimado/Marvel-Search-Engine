package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import java.awt.*;

abstract class TemplateSearchListRenderer extends JPanel {
    private final ImageIcon imageIcon;
    private final JLabel label;
    private final JPanel imagePanel;
    private boolean selected;
    private Object data;

    TemplateSearchListRenderer(ImageIcon icon) {
        this.label = new JLabel();
        this.imagePanel = new JPanel();
        this.imageIcon = icon;
        this.init();
    }

    private void init() {
        final Dimension panelDimension = new Dimension(200, 60);
        this.setPreferredSize(panelDimension);
        this.setMaximumSize(panelDimension);
        this.setMinimumSize(panelDimension);

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));

        this.label.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        this.add(this.label, BorderLayout.CENTER);

        final JLabel picture = new JLabel();
        picture.setIcon(imageIcon);
        this.imagePanel.setLayout(new BorderLayout());
        this.imagePanel.add(picture, BorderLayout.CENTER);
        Dimension imageDimension = new Dimension(50, 60);
        this.imagePanel.setPreferredSize(imageDimension);
        this.imagePanel.setMaximumSize(imageDimension);
        this.imagePanel.setMinimumSize(imageDimension);
        this.imagePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
        this.add(this.imagePanel, BorderLayout.WEST);
    }

    abstract protected String getTitle(Object o);


    @Override
    public void paintComponent(Graphics graphics) {
        this.label.setText(getTitle(this.data));
        if (this.selected) {
            this.setBackground(Color.BLUE);
        }
        super.paintComponent(graphics);
    }

    public boolean isSelected() {
        return selected;
    }

    void setSelected(boolean selected) {
        this.selected = selected;
        this.setBackground(this.selected ? Color.BLUE : Color.WHITE);
        this.label.setForeground(this.selected ? Color.WHITE : Color.BLACK);
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
