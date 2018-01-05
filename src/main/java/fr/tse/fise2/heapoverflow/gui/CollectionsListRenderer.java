package fr.tse.fise2.heapoverflow.gui;

import fr.tse.fise2.heapoverflow.database.CollectionsRow;

import javax.swing.*;
import java.awt.*;

public class CollectionsListRenderer implements ListCellRenderer {
    private final ListPanel renderer;

    public CollectionsListRenderer() {
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
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        return renderer;
    }


    class ListPanel extends JPanel {
        private final JPanel imagePanel;
        private final JLabel collectionLabel;
        private final JLabel collectionDescription;
        private CollectionsRow collectionsRow;


        /**
         * Creates a new <code>JPanel</code> with a double buffer
         * and a flow layout.
         */
        public ListPanel() {
            super();


            this.imagePanel = new JPanel();
            this.collectionLabel = new JLabel("hd jd dhj djh ddjhgd  dhjd ");
            this.collectionDescription = new JLabel("desc dghfdu fhfiusj jsdhjfh sdjhfisd fsudjhg ");

            this.setBorder(BorderFactory.createLineBorder(UIColor.PRIMARY_COLOR));

            this.setMinimumSize(new Dimension(300, 100));
            this.setPreferredSize(new Dimension(300, 100));
            this.setMaximumSize(new Dimension(300, 100));


            this.imagePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));


            this.initComponent();
        }

        private void initComponent() {
            // image planel
            this.imagePanel.setPreferredSize(new Dimension(100, 100));


            GroupLayout groupLayout = new GroupLayout(this);
            this.setLayout(groupLayout);


            groupLayout
                    .setHorizontalGroup(
                            groupLayout.createSequentialGroup()
                                    .addComponent(this.imagePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGap(10)
                                    .addGroup(
                                            groupLayout.createParallelGroup()
                                                    .addComponent(this.collectionLabel)
                                                    .addComponent(this.collectionDescription)
                                    )
                    );


            groupLayout
                    .setVerticalGroup(
                            groupLayout.createParallelGroup()
                                    .addComponent(this.imagePanel)

                                    .addGroup(
                                            groupLayout.createSequentialGroup()
                                                    .addGap(10)
                                                    .addComponent(this.collectionLabel)
                                                    .addGap(10)
                                                    .addComponent(this.collectionDescription)
                                    )
                    );
        }

    }


}
