package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.database.CollectionsRow;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

public class CollectionsListRenderer implements ListCellRenderer<CollectionsRow> {
    private final ListPanel renderer;

    CollectionsListRenderer() {
        this.renderer = new ListPanel();
    }

    /**
     * Return a component that has been configured to display the specified
     * value. That component's <code>paint</code> method is then called to
     * "render" the cell.  If it is necessary to compute the dimensions
     * of a list because the list cells do not have a fixed size, this method
     * is called to generate a component on which <code>getPreferredSize</code>
     * can be invoked.
     *
     * @param list         The JList we're painting.
     * @param value        The value returned by list.getModel().getElementAt(index).
     * @param index        The cells index.
     * @param isSelected   True if the specified cell was selected.
     * @param cellHasFocus True if the specified cell has the focus.
     * @return A component whose paint() method will render the specified value.
     * @see JList
     * @see ListSelectionModel
     * @see ListModel
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends CollectionsRow> list, CollectionsRow value, int index, boolean isSelected, boolean cellHasFocus) {
        renderer.setSelected(isSelected);
        renderer.setCollectionsRow(value);
        return renderer;
    }

    private static class ListPanel extends JPanel {
        private final static ImageIcon IMAGE_ICON = new ImageIcon(ListPanel.class.getResource("c_for_collections.png"));

        private final JPanel imagePanel;
        private final JLabel collectionLabel;
        private final JLabel collectionDescription;
        private final JPanel mainPanel;
        private CollectionsRow collectionsRow;
        private boolean selected;

        /**
         * Creates a new <code>JPanel</code> with a double buffer
         * and a flow layout.
         */
        ListPanel() {
            super(new BorderLayout());

            this.setOpaque(true);

            this.setBackground(UIColor.LiST_CELL_SELECTED);


            final JLabel picture = new JLabel();
            picture.setIcon(IMAGE_ICON);
            this.imagePanel = new JPanel(new BorderLayout());
            this.imagePanel.add(picture, BorderLayout.CENTER);


            this.collectionLabel = new JLabel();
            this.collectionLabel.setOpaque(false);
            this.collectionDescription = new JLabel();
            this.collectionDescription.setOpaque(false);
            this.setBorder(BorderFactory.createLineBorder(UIColor.HEADER_SHADOW_COLOR));

            this.setMinimumSize(new Dimension(300, 100));
            this.setPreferredSize(new Dimension(300, 100));
            this.setMaximumSize(new Dimension(300, 100));

            this.mainPanel = new JPanel(new BorderLayout());


            this.initComponent();
        }

        /**
         * Calls the UI delegate's paint method, if the UI delegate
         * is non-<code>null</code>.  We pass the delegate a copy of the
         * <code>Graphics</code> object to protect the rest of the
         * paint code from irrevocable changes
         * (for example, <code>Graphics.translate</code>).
         * <p>
         * If you override this in a subclass you should not make permanent
         * changes to the passed in <code>Graphics</code>. For example, you
         * should not alter the clip <code>Rectangle</code> or modify the
         * transform. If you need to do these operations you may find it
         * easier to create a new <code>Graphics</code> from the passed in
         * <code>Graphics</code> and manipulate it. Further, if you do not
         * invoker super's implementation you must honor the opaque property,
         * that is
         * if this component is opaque, you must completely fill in the background
         * in a non-opaque color. If you do not honor the opaque property you
         * will likely see visual artifacts.
         * <p>
         * The passed in <code>Graphics</code> object might
         * have a transform other than the identify transform
         * installed on it.  In this case, you might get
         * unexpected results if you cumulatively apply
         * another transform.
         *
         * @param g the <code>Graphics</code> object to protect
         * @see #paint
         * @see ComponentUI
         */
        @Override
        protected void paintComponent(Graphics g) {
            if (this.selected) {
                this.mainPanel.setBackground(UIColor.LiST_CELL_SELECTED);
            } else {
                this.mainPanel.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
            }
        }

        private void initComponent() {
            // image panel
            this.imagePanel.setPreferredSize(new Dimension(100, 100));

            // main panel
            this.mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            this.mainPanel.add(this.collectionLabel, BorderLayout.NORTH);
            this.mainPanel.add(this.collectionDescription, BorderLayout.CENTER);

            this.add(this.imagePanel, BorderLayout.WEST);
            this.add(this.mainPanel, BorderLayout.CENTER);
        }

        void setSelected(boolean selected) {
            this.selected = selected;
            if (this.selected) {
                this.mainPanel.setBackground(UIColor.LiST_CELL_SELECTED);
            } else {
                this.mainPanel.setBackground(UIColor.MAIN_BACKGROUND_COLOR);
            }
        }

        void setCollectionsRow(CollectionsRow collectionsRow) {
            this.collectionsRow = collectionsRow;
            this.collectionLabel.setText(this.collectionsRow.getTitle());
            this.collectionDescription.setText(this.collectionsRow.getDescription());
        }
    }


}
