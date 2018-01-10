package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.gui.UI;

import java.awt.*;

public class Launcher {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            UI ui = new UI();
            ui.init();
            AppErrorHandler.configureLogging();
            Controller controller = new Controller(ui);
            controller.init();
        });
    }
}