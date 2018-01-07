package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.gui.UI;

import javax.swing.*;
import java.awt.*;

public class main {
    public static void main(String[] args) {

        UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(1, 0, 0, 0));
        UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", true);

        UI ui = new UI();

        ui.init();
        AppErrorHandler.configureLogging();
        Controller controller = new Controller(ui);
        controller.init();


    }
}
