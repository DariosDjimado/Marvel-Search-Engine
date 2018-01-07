package fr.tse.fise2.heapoverflow.gui;

import javax.swing.*;

class DecorateButtonFormat extends ButtonFormat {


    DecorateButtonFormat(ImageIcon icon) {
        super();
        this.setIcon(icon);
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, UIColor.HEADER_SHADOW_COLOR));

        setBackground(UIColor.MAIN_BACKGROUND_COLOR);

        setContentAreaFilled(true);

    }

}
