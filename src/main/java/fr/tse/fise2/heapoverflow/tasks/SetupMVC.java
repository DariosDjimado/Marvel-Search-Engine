package fr.tse.fise2.heapoverflow.tasks;

import fr.tse.fise2.heapoverflow.controllers.SetupController;
import fr.tse.fise2.heapoverflow.gui.SetupView;
import fr.tse.fise2.heapoverflow.models.SetupModel;

import java.awt.*;

public class SetupMVC {

    public SetupMVC() {
        SetupModel model = new SetupModel();
        SetupController controller = new SetupController(model);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SetupView view = new SetupView(model, controller);
                controller.setView(view);
                model.addObserver(view);
            }
        });


    }

    public static void main(String[] args) {
        new SetupMVC();

    }
}
