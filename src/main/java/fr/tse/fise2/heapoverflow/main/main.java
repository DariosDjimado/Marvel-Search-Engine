package fr.tse.fise2.heapoverflow.main;

import fr.tse.fise2.heapoverflow.gui.UI;

public class main {
    public static void main(String[] args) {
        UI ui = new UI();

        ui.init();
        AppErrorHandler.configureLogging();
        Controller controller = new Controller(ui);
        controller.init();


    }
}