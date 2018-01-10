package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;
import java.util.List;

class ExternalLinksView extends JPanel {

    ExternalLinksView(List<LinkView> links) {
        GroupLayout groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);
        this.setBackground(UIColor.MAIN_BACKGROUND_COLOR);

        GroupLayout.Group horizontalGroup = groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        for (LinkView link : links) {
            horizontalGroup.addComponent(link, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        }

        GroupLayout.Group verticalGroup = groupLayout.createSequentialGroup();
        for (LinkView link : links) {
            verticalGroup.addComponent(link);
        }
        groupLayout.setHorizontalGroup(horizontalGroup);
        groupLayout.setVerticalGroup(verticalGroup);

    }
}
