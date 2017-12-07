package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.gui.UI;

import java.awt.*;

public class main {
    public static void main(String[] args) {
        UI ui = new UI();

        EventQueue.invokeLater(ui::init);
        AppLogger appLogger = new AppLogger(ui);
        appLogger.configureLogging();
        Controller controller = new Controller(ui, appLogger);
        controller.init();


    }
}
